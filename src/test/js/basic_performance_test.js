import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
	vus: 100,
	duration: '30s'
};

export default function() {
	const response = http.get('http://localhost:8080/building/480414');
	if (response.status !== 200) {
		console.error('Error:', response.status, response.body);
		if (response.status === 429) {
			sleep(1);
		}
	}
}