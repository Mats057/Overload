import http from 'k6/http'
import { check, sleep } from 'k6'

export const options = {
  stages: [
    { duration: '10s', target: 200 },
    { duration: '1m', target: 200 },
    { duration: '30s', target: 0 },
  ],
  thresholds: { http_req_duration: ['avg<100', 'p(95)<200'] },
  noConnectionReuse: true,
  userAgent: 'MyK6UserAgentString/1.0',
};


export default function () {
    const data = { payload: 'jahdfukasiudgaisugduasgdkugaskdasgdkasgp[qicjsak.jcuahgueiqkldhnjaklscjeopiasfyhioqwaehqoweqw654df6a5d4h65hfg4k6hj54olhjk564ikg54n56df4fx56dzjahdfukasiudgaisugduasgdkugaskdasgdkasgp[qicjsak.jcuahgueiqkldhnjaklscjeopiasfyhioqwaehqoweqw654df6a5d4h65hfg4k6hj54olhjk564ikg54n56df4fx56dzjahdfukasiudgaisugduasgdkugaskdasgdkasgp[qicjsak.jcuahgueiqkldhnjaklscjeopiasfyhioqwaehqoweqw654df6a5d4h65hfg4k6hj54olhjk564ikg54n56df4fx56dzjhljhjahdfukasiudgaisugduasgdkugaskdasgdkasgp[qicjsak.jcuahgueiqkldhnjaklscjeopiasfyhioqwaehqoweqw654df6a5d4h65hfg4k6hj54olhjk564ikg54n56df4fx56dzkjghkhjgakdgaksjgdjkashgjahdfukasiudgaisugduasgdkugaskdasgdkasgp[qicjsak.jcuahgueiqkldhnjaklscjeopiasfyhioqwaehqoweqw654df6a5d4h65hfg4k6hj54olhjk564ikg54n56df4fx56dzjhajshdkjashdjkhawuileryqwoiueoiqwjahdfukasiudgaisugduasgdkugaskdasgdkasgp[qicjsak.jcuahgueiqkldhnjaklscjeopiasfyhioqwaehqoweqw654df6a5d4h65hfg4k6hj54olhjk564ikg54n56df4fx56dz1239-08490qut0ywergoidsuoifyjahdfukasiudgaisugduasgdkugaskdasgdkasgp[qicjsak.jcuahgueiqkldhnjaklscjeopiasfyhioqwaehqoweqw654df6a5d4h65hfg4k6hj54olhjk564ikg54n56df4fx56dz902873409723097ru9pqwujahdfukasiudgaisugduasgdkugaskdasgdkasgp[qicjsak.jcuahgueiqkldhnjaklscjeopiasfyhioqwaehqoweqw654df6a5d4h65hfg4k6hj54olhjk564ikg54n56df4fx56dzjasedjwqop4u2390urfaf' }
    let res = http.post('http://localhost:8080/sort', data)

    console.log(res.status);

    check(res, { 'success request': (r) => r.status === 200 })

    sleep(0.3)
}
