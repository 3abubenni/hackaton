"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.Auth = void 0;
var _SignIn = require("../SignIn/app/SignIn");
require("../styles/Authstyles.css");
var _Header = require("../Header/Header");
var Auth = exports.Auth = function Auth() {
  return /*#__PURE__*/React.createElement("div", {
    id: "auth"
  }, /*#__PURE__*/React.createElement(_Header.Header, null), /*#__PURE__*/React.createElement("div", {
    id: "form"
  }, /*#__PURE__*/React.createElement(_SignIn.SignIn, null)));
};