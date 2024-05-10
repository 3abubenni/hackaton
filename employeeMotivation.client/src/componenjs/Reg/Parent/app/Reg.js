"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.Reg = void 0;
var _Header = require("../../../Auth/Header/app/Header");
var _SignUp = require("../../SignUp/app/SignUp");
require("../styles/Regstyles.css");
var Reg = exports.Reg = function Reg() {
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("div", {
    id: "reg"
  }, /*#__PURE__*/React.createElement(_Header.Header, null), /*#__PURE__*/React.createElement("div", {
    id: "form"
  }, /*#__PURE__*/React.createElement(_SignUp.SignUp, null))));
};