import http from 'k6/http'
import { check, sleep } from 'k6'
import { Trend } from 'k6/metrics';
import { randomIntBetween, randomString } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

const submitDuration = new Trend('submit_duration', true);

export const options = {
  scenarios: {
    submit_load: {
      executor: 'ramping-vus',
      exec: 'submitOnly',
      startVUs: 0,
      stages: [
        { duration: '10s', target: 5000 },
        { duration: '1m', target: 5000 },
        { duration: '30s', target: 0 },
      ],
      gracefulRampDown: '30s',
    },
    // completion_probe: {
    //   executor: 'constant-vus',
    //   exec: 'submitAndPoll',
    //   vus: 20,
    //   duration: '1m40s',
    //   gracefulStop: '30s',
    // },
  },
  thresholds: {
    http_req_duration: ['avg<500', 'p(95)<1000'],
    submit_duration: ['avg<200', 'p(95)<500'],
  },
  userAgent: 'MyK6UserAgentString/1.0',
};

function submitJob() {
  const randomSize = randomIntBetween(1000, 20000);
  const payload = randomString(randomSize);
  const res = http.post('http://localhost:8080/sort', payload, {
    headers: { 'Content-Type': 'text/plain' },
  });

  submitDuration.add(res.timings.duration);
  const location = res.headers.Location || res.headers.location || '';
  const jobId = location.split('/').filter(Boolean).pop() || null;

  check(res, {
    'accepted request': (r) => r.status === 202,
    'returned location header': () => !!location,
    'location contains job id': () => /^\/sort\/[0-9a-fA-F-]+$/.test(location),
  });

  return {
    response: res,
    location,
    jobId,
  };
}

export function submitOnly() {
  submitJob();
  sleep(0.3);
}
