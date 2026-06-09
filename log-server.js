const http = require('http');
const fs = require('fs');
const path = require('path');

const LOG_DIR = path.join(__dirname, 'logs');
const PORT = 9090;

const SERVICES = [
  { id: 'user-service',      label: 'User Service',      port: 8081, color: '#6366f1' },
  { id: 'product-service',   label: 'Product Service',   port: 8082, color: '#10b981' },
  { id: 'order-service',     label: 'Order Service',     port: 8083, color: '#f59e0b' },
  { id: 'inventory-service', label: 'Inventory Service', port: 8084, color: '#3b82f6' },
  { id: 'quotation-service', label: 'Quotation Service', port: 8085, color: '#8b5cf6' },
  { id: 'billing-service',      label: 'Billing Service',   port: 8086, color: '#ec4899' },
  { id: 'api-gateway',          label: 'API Gateway',       port: 8087, color: '#14b8a6' },
  { id: 'notification-service', label: 'Notification Svc',  port: 8089, color: '#f97316' },
  { id: 'eureka-server',     label: 'Eureka Server',     port: 8761, color: '#f97316' },
  { id: 'config-server',     label: 'Config Server',     port: 8888, color: '#64748b' }
];

// Read last N lines of a file
function readLastLines(filePath, maxLines = 500) {
  try {
    if (!fs.existsSync(filePath)) return [];
    const content = fs.readFileSync(filePath, 'utf8');
    const lines = content.split('\n').filter(l => l.trim());
    return lines.slice(-maxLines);
  } catch (e) {
    return [];
  }
}

// Parse a log line into structured object
function parseLine(line, serviceId) {
  const service = SERVICES.find(s => s.id === serviceId);

  // Match: 2026-06-07T12:40:59.538+05:30  INFO 9148 --- ...
  const springMatch = line.match(/^(\d{4}-\d{2}-\d{2}T[\d:.+]+)\s+(INFO|WARN|ERROR|DEBUG|TRACE)\s+\d+\s+---\s+\[([^\]]*)\]\s+([^\s:]+)\s*:\s*(.+)$/);
  if (springMatch) {
    return {
      timestamp: springMatch[1].replace('T', ' ').substring(0, 19),
      level: springMatch[2],
      thread: springMatch[3].trim(),
      logger: springMatch[4],
      message: springMatch[5],
      service: serviceId,
      serviceLabel: service?.label || serviceId,
      serviceColor: service?.color || '#64748b',
      raw: line
    };
  }

  // Match: 14:33:05 [INFO ] c.g.u.c.AuthController - message
  const shortMatch = line.match(/^(\d{2}:\d{2}:\d{2})\s+\[([\w\s]+)\]\s+(\S+)\s+-\s+(.+)$/);
  if (shortMatch) {
    return {
      timestamp: shortMatch[1],
      level: shortMatch[2].trim(),
      logger: shortMatch[3],
      message: shortMatch[4],
      service: serviceId,
      serviceLabel: service?.label || serviceId,
      serviceColor: service?.color || '#64748b',
      raw: line
    };
  }

  // Maven [INFO] lines
  const mavenMatch = line.match(/^\[(\w+)\]\s+(.+)$/);
  if (mavenMatch) {
    return {
      timestamp: '',
      level: mavenMatch[1],
      logger: 'maven',
      message: mavenMatch[2],
      service: serviceId,
      serviceLabel: service?.label || serviceId,
      serviceColor: service?.color || '#64748b',
      raw: line
    };
  }

  return {
    timestamp: '',
    level: 'INFO',
    logger: '',
    message: line,
    service: serviceId,
    serviceLabel: service?.label || serviceId,
    serviceColor: service?.color || '#64748b',
    raw: line
  };
}

const server = http.createServer((req, res) => {
  // CORS
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

  if (req.method === 'OPTIONS') { res.writeHead(200); res.end(); return; }

  const url = new URL(req.url, `http://localhost:${PORT}`);

  // GET /services - list all services
  if (url.pathname === '/services') {
    res.setHeader('Content-Type', 'application/json');
    res.writeHead(200);
    res.end(JSON.stringify(SERVICES));
    return;
  }

  // GET /logs?service=all&level=all&search=&lines=500&minutes=5
  if (url.pathname === '/logs') {
    const serviceFilter = url.searchParams.get('service') || 'all';
    const levelFilter   = url.searchParams.get('level')   || 'all';
    const searchFilter  = url.searchParams.get('search')  || '';
    const maxLines      = parseInt(url.searchParams.get('lines') || '500');
    const minutes       = parseInt(url.searchParams.get('minutes') || '0'); // 0 = all
    const sinceMs       = minutes > 0 ? Date.now() - minutes * 60 * 1000 : 0;

    const servicesToRead = serviceFilter === 'all'
      ? SERVICES.map(s => s.id)
      : [serviceFilter];

    let allEntries = [];

    for (const svcId of servicesToRead) {
      const logFile = path.join(LOG_DIR, `${svcId}.log`);
      const lines = readLastLines(logFile, maxLines);
      const parsed = lines.map(l => parseLine(l, svcId));
      allEntries = allEntries.concat(parsed);
    }

    // Filter by level
    if (levelFilter !== 'all') {
      allEntries = allEntries.filter(e => e.level.toUpperCase() === levelFilter.toUpperCase());
    }

    // Filter by search
    if (searchFilter) {
      const q = searchFilter.toLowerCase();
      allEntries = allEntries.filter(e =>
        e.message.toLowerCase().includes(q) ||
        e.logger.toLowerCase().includes(q) ||
        e.service.toLowerCase().includes(q)
      );
    }

    // Filter by time (last N minutes)
    if (sinceMs > 0) {
      allEntries = allEntries.filter(e => {
        if (!e.timestamp) return true;
        try {
          // Handle both "HH:mm:ss" and "yyyy-MM-dd HH:mm:ss" formats
          const ts = e.timestamp.includes('T') ? e.timestamp
            : e.timestamp.length === 8
              ? new Date().toISOString().slice(0,10) + 'T' + e.timestamp
              : e.timestamp.replace(' ', 'T');
          return new Date(ts).getTime() >= sinceMs;
        } catch { return true; }
      });
    }

    // Sort by timestamp (best effort)
    allEntries.sort((a, b) => {
      if (!a.timestamp || !b.timestamp) return 0;
      return a.timestamp.localeCompare(b.timestamp);
    });

    res.setHeader('Content-Type', 'application/json');
    res.writeHead(200);
    res.end(JSON.stringify({ entries: allEntries.slice(-maxLines), total: allEntries.length }));
    return;
  }

  // GET /logs/stream - Server-Sent Events for live tail
  if (url.pathname === '/logs/stream') {
    res.setHeader('Content-Type', 'text/event-stream');
    res.setHeader('Cache-Control', 'no-cache');
    res.setHeader('Connection', 'keep-alive');
    res.writeHead(200);

    const watchers = [];
    const lastSizes = {};

    for (const svc of SERVICES) {
      const logFile = path.join(LOG_DIR, `${svc.id}.log`);
      try {
        lastSizes[svc.id] = fs.existsSync(logFile) ? fs.statSync(logFile).size : 0;
        const watcher = fs.watch(logFile, { persistent: false }, () => {
          try {
            const stat = fs.statSync(logFile);
            if (stat.size <= lastSizes[svc.id]) return;
            const fd = fs.openSync(logFile, 'r');
            const len = stat.size - lastSizes[svc.id];
            const buf = Buffer.alloc(len);
            fs.readSync(fd, buf, 0, len, lastSizes[svc.id]);
            fs.closeSync(fd);
            lastSizes[svc.id] = stat.size;
            const newLines = buf.toString('utf8').split('\n').filter(l => l.trim());
            for (const line of newLines) {
              const entry = parseLine(line, svc.id);
              res.write(`data: ${JSON.stringify(entry)}\n\n`);
            }
          } catch (e) {}
        });
        watchers.push(watcher);
      } catch (e) {}
    }

    // Send heartbeat every 15s
    const heartbeat = setInterval(() => {
      res.write(': heartbeat\n\n');
    }, 15000);

    req.on('close', () => {
      clearInterval(heartbeat);
      watchers.forEach(w => { try { w.close(); } catch(e){} });
    });
    return;
  }

  // GET /stats - service stats
  if (url.pathname === '/stats') {
    const stats = SERVICES.map(svc => {
      const logFile = path.join(LOG_DIR, `${svc.id}.log`);
      const lines = readLastLines(logFile, 1000);
      const parsed = lines.map(l => parseLine(l, svc.id));
      return {
        ...svc,
        exists: fs.existsSync(logFile),
        totalLines: lines.length,
        errors: parsed.filter(e => e.level === 'ERROR').length,
        warns:  parsed.filter(e => e.level === 'WARN').length,
        lastActivity: parsed.length > 0 ? parsed[parsed.length - 1].timestamp : null
      };
    });
    res.setHeader('Content-Type', 'application/json');
    res.writeHead(200);
    res.end(JSON.stringify(stats));
    return;
  }

  res.writeHead(404);
  res.end('Not found');
});

server.on('error', (err) => {
  if (err.code === 'EADDRINUSE') {
    console.error(`\n❌ Port ${PORT} is already in use.`);
    console.error(`   Run this to fix it: taskkill /F /PID $(netstat -ano | findstr :${PORT})`);
    console.error(`   Or simply close the other log server window and try again.\n`);
    process.exit(1);
  } else {
    throw err;
  }
});

server.listen(PORT, () => {
  console.log('');
  console.log('╔══════════════════════════════════════════════╗');
  console.log('║   Godavari Log Server running on port 9090  ║');
  console.log('╠══════════════════════════════════════════════╣');
  console.log('║  Endpoints:                                  ║');
  console.log('║  GET /logs       - All logs (paginated)      ║');
  console.log('║  GET /logs/stream- Live SSE stream           ║');
  console.log('║  GET /services   - Service list              ║');
  console.log('║  GET /stats      - Service stats             ║');
  console.log('╚══════════════════════════════════════════════╝');
  console.log('');
  console.log(`Log directory: ${LOG_DIR}`);
});
