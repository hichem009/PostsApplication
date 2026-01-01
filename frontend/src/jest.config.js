/** @type {import('ts-jest').JestConfigWithTsJest} */
module.exports = {
    preset: 'ts-jest',
    testEnvironment: 'node',  // 'node' is fine for API testing, no need for jsdom
    testMatch: ["**/__tests__/**/*.test.ts"], // match your test files
  };
  