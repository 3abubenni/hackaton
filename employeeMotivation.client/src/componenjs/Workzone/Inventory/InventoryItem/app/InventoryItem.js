"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.InventoryItem = void 0;
require("../styles/InventoryItemstyles.css");
var InventoryItem = exports.InventoryItem = function InventoryItem(_ref) {
  var name = _ref.name,
    image = _ref.image;
  return /*#__PURE__*/React.createElement("div", {
    className: "inventoryItem"
  }, /*#__PURE__*/React.createElement("div", {
    id: "inventoryItemContent"
  }, /*#__PURE__*/React.createElement("img", {
    src: image,
    alt: "123"
  }), /*#__PURE__*/React.createElement("p", null, name)));
};