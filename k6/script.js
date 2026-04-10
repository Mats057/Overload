import http from 'k6/http'
import { check, sleep } from 'k6'
import { randomIntBetween, randomString } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export const options = {
  stages: [
    { duration: '10s', target: 2000 },
    { duration: '1m', target: 2000 },
    { duration: '30s', target: 0 },
  ],
  thresholds: { http_req_duration: ['avg<500', 'p(95)<1000'] },
  noConnectionReuse: true,
  userAgent: 'MyK6UserAgentString/1.0',
};

export default function () {

  const randomSize = randomIntBetween(5, 2000);
  const payload = randomString(randomSize);
  const data = { payload }
  let res = http.post('http://localhost:8080/sort', data)

  check(res, { 'success request': (r) => r.status === 200 })

  sleep(0.3)
}
