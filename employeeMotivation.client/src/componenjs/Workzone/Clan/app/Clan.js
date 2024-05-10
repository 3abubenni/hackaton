"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.Clan = void 0;
require("../styles/Clanstyles.css");
var Clan = exports.Clan = function Clan(_ref) {
  var name = _ref.name;
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("div", {
    id: "clan"
  }, /*#__PURE__*/React.createElement("div", {
    className: "clanElemnts"
  }, /*#__PURE__*/React.createElement("div", null), /*#__PURE__*/React.createElement("p", null, name), /*#__PURE__*/React.createElement("button", null, "Join"))));
};