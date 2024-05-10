"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.StoreItem = void 0;
require("../styles/StoreItemstyles.css");
var StoreItem = exports.StoreItem = function StoreItem(_ref) {
  var name = _ref.name,
    image = _ref.image;
  return /*#__PURE__*/React.createElement("div", {
    className: "storeItem"
  }, /*#__PURE__*/React.createElement("div", {
    id: "storeItemContent"
  }, /*#__PURE__*/React.createElement("img", {
    src: image,
    alt: "123"
  }), /*#__PURE__*/React.createElement("p", null, name)));
};