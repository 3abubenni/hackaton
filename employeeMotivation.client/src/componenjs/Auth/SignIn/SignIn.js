"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.SignIn = void 0;
var _react = _interopRequireDefault(require("react"));
require("../SignIn/styles/signInstyles.css");
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
var SignIn = exports.SignIn = function SignIn() {
  return /*#__PURE__*/_react.default.createElement("div", {
    id: "container"
  }, /*#__PURE__*/_react.default.createElement("h1", null, "Log In"), /*#__PURE__*/_react.default.createElement("input", {
    type: "text",
    placeholder: "Email"
  }), /*#__PURE__*/_react.default.createElement("input", {
    type: "password",
    placeholder: "Password"
  }), /*#__PURE__*/_react.default.createElement("div", {
    className: "remindPass"
  }, /*#__PURE__*/_react.default.createElement("a", {
    href: ""
  }, "Forgot password?")), /*#__PURE__*/_react.default.createElement("div", {
    id: "buttons"
  }, /*#__PURE__*/_react.default.createElement("button", null, "Sign in"), /*#__PURE__*/_react.default.createElement("button", null, "Registration")));
};