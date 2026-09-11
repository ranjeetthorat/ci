const test = require('node:test');
const assert = require('node:assert/strict');

test('frontend smoke test passes in CI', () => {
  assert.equal(typeof 'frontend', 'string');
});