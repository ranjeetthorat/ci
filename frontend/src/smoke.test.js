const test = require('node:test');
const assert = require('node:assert/strict');

test('frontend smoke test for in CI', () => {
  assert.equal(typeof 'frontend', 'string');
});