spring:
  sql:
    init:
      mode: always
      data-locations: classpath:data.sql
  jpa:
    defer-datasource-initialization: true
