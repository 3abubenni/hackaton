"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.Header = void 0;
var _ai = require("react-icons/ai");
require("./Header.css");
var _reactRouterDom = require("react-router-dom");
var Header = exports.Header = function Header() {
  var navigate = (0, _reactRouterDom.useNavigate)();
  var GoToLogIn = function GoToLogIn() {
    navigate("/auth");
  };
  return /*#__PURE__*/React.createElement("div", {
    id: "headerContainer"
  }, /*#__PURE__*/React.createElement("div", {
    id: "iconCloud",
    onClick: GoToLogIn
  }, /*#__PURE__*/React.createElement(_ai.AiFillCloud, null)));
};