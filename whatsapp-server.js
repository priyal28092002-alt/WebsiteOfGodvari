const wppconnect = require('@wppconnect-team/wppconnect');
const http = require('http');

const PORT = 9091;
let client = null;
let status = 'STARTING'; // STARTING | QR_READY | CONNECTED | DISCONNECTED

// ── Start WPPConnect ──────────────────────────────────────────────────────────
wppconnect.create({
  session: 'godavari-whatsapp',
  catchQR: (base64Qr, asciiQR) => {
    status = 'QR_READY';
    console.log('\n╔══════════════════════════════════════════════╗');
    console.log('║   SCAN THIS QR CODE WITH YOUR WHATSAPP      ║');
    console.log('╚══════════════════════════════════════════════╝\n');
    console.log(asciiQR);
    console.log('\n👆 Open WhatsApp → Linked Devices → Link a Device → Scan above QR\n');
  },
  statusFind: (statusSession) => {
    console.log('==> WPPConnect status:', statusSession);
    if (statusSession === 'isLogged' || statusSession === 'notLogged') {
      status = statusSession === 'isLogged' ? 'CONNECTED' : 'DISCONNECTED';
    }
  },
  headless: true,
  devtools: false,
  useChrome: true,
  debug: false,
  logQR: true,
  browserWS: '',
  browserArgs: ['--no-sandbox', '--disable-setuid-sandbox'],
  puppeteerOptions: {},
  disableWelcome: true,
  updatesLog: false,
  autoClose: 0,
  tokenStore: 'file',
  folderNameToken: './whatsapp-session',
  createPathFileToken: true,
})
.then(c => {
  client = c;
  status = 'CONNECTED';
  console.log('\n✅ WhatsApp Connected! Ready to send messages.');
  console.log(`📡 API running on http://localhost:${PORT}`);
  console.log('📌 Endpoints:');
  console.log('   POST /send  { "phone": "919876543210", "message": "Hello" }');
  console.log('   GET  /status\n');
})
.catch(err => {
  status = 'ERROR';
  console.error('❌ WPPConnect error:', err.message);
});

// ── HTTP Server ───────────────────────────────────────────────────────────────
const server = http.createServer(async (req, res) => {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
  res.setHeader('Content-Type', 'application/json');

  if (req.method === 'OPTIONS') { res.writeHead(200); res.end(); return; }

  // GET /status
  if (req.method === 'GET' && req.url === '/status') {
    res.writeHead(200);
    res.end(JSON.stringify({ status, connected: status === 'CONNECTED' }));
    return;
  }

  // POST /send
  if (req.method === 'POST' && req.url === '/send') {
    let body = '';
    req.on('data', chunk => body += chunk);
    req.on('end', async () => {
      try {
        const { phone, message } = JSON.parse(body);

        if (!phone || !message) {
          res.writeHead(400);
          res.end(JSON.stringify({ success: false, error: 'phone and message are required' }));
          return;
        }

        if (status !== 'CONNECTED' || !client) {
          res.writeHead(503);
          res.end(JSON.stringify({ success: false, error: `WhatsApp not connected. Status: ${status}` }));
          return;
        }

        // Normalize: remove + prefix, ensure 91 prefix for India
        let normalized = phone.replace(/\D/g, '');
        if (normalized.startsWith('0')) normalized = '91' + normalized.substring(1);
        if (!normalized.startsWith('91') && normalized.length === 10) normalized = '91' + normalized;
        const chatId = normalized + '@c.us';

        console.log(`\n📤 =============================================`);
        console.log(`📤 WHATSAPP SEND REQUEST`);
        console.log(`📤 To     : ${phone}`);
        console.log(`📤 ChatId : ${chatId}`);
        console.log(`📤 Message: ${message.substring(0, 80)}...`);
        console.log(`📤 =============================================`);
        await client.sendText(chatId, message);
        console.log(`✅ =============================================`);
        console.log(`✅ MESSAGE SENT SUCCESSFULLY`);
        console.log(`✅ To: ${chatId}`);
        console.log(`✅ =============================================\n`);

        res.writeHead(200);
        res.end(JSON.stringify({ success: true, to: chatId }));
      } catch (e) {
        console.error(`\n❌ =============================================`);
        console.error(`❌ WHATSAPP SEND FAILED`);
        console.error(`❌ To   : ${phone}`);
        console.error(`❌ Error: ${e.message}`);
        console.error(`❌ =============================================\n`);
        res.writeHead(500);
        res.end(JSON.stringify({ success: false, error: e.message }));
      }
    });
    return;
  }

  res.writeHead(404);
  res.end(JSON.stringify({ error: 'Not found' }));
});

server.listen(PORT, () => {
  console.log('\n╔══════════════════════════════════════════════╗');
  console.log('║   Godavari WhatsApp Server - Port 9091      ║');
  console.log('╚══════════════════════════════════════════════╝');
  console.log('⏳ Starting WhatsApp connection...');
  console.log('   First time: QR code will appear — scan with your WhatsApp\n');
});
