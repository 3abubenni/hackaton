"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.InventoryItem = void 0;
require("../styles/InventoryItemstyles.css");
var _Items = require("../../../../../entities/Items.interface");
var InventoryItem = exports.InventoryItem = function InventoryItem(_ref) {
  var name = _ref.name,
    count = _ref.count;
  return /*#__PURE__*/React.createElement("div", {
    className: "inventoryItem"
  }, /*#__PURE__*/React.createElement("div", {
    id: "inventoryItemContent"
  }, /*#__PURE__*/React.createElement("p", null, name), /*#__PURE__*/React.createElement("p", {
    id: "count"
  }, "Count: ", count)));
};