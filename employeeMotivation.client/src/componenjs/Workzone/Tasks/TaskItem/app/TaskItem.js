"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;
var _react = require("react");
var _Items = require("../../../../../entities/Items.interface");
require("../styles/TaskItemstyles.css");
var _reactModal = _interopRequireDefault(require("react-modal"));
var _MyModalTaskItem = require("../../../../MyModal/ModalItems/TaskItem/app/MyModalTaskItem");
var _customStyleModal = require("../../../../../helpers/styles/customStyleModal");
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
function _slicedToArray(arr, i) { return _arrayWithHoles(arr) || _iterableToArrayLimit(arr, i) || _unsupportedIterableToArray(arr, i) || _nonIterableRest(); }
function _nonIterableRest() { throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }
function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }
function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) arr2[i] = arr[i]; return arr2; }
function _iterableToArrayLimit(r, l) { var t = null == r ? null : "undefined" != typeof Symbol && r[Symbol.iterator] || r["@@iterator"]; if (null != t) { var e, n, i, u, a = [], f = !0, o = !1; try { if (i = (t = t.call(r)).next, 0 === l) { if (Object(t) !== t) return; f = !1; } else for (; !(f = (e = i.call(t)).done) && (a.push(e.value), a.length !== l); f = !0); } catch (r) { o = !0, n = r; } finally { try { if (!f && null != t.return && (u = t.return(), Object(u) !== u)) return; } finally { if (o) throw n; } } return a; } }
function _arrayWithHoles(arr) { if (Array.isArray(arr)) return arr; }
var TaskItem = function TaskItem(_ref) {
  var id = _ref.id,
    title = _ref.title,
    description = _ref.description,
    exp = _ref.exp,
    money = _ref.money,
    remove = _ref.remove,
    name = _ref.name,
    status = _ref.status,
    type = _ref.type;
  var _useState = (0, _react.useState)(false),
    _useState2 = _slicedToArray(_useState, 2),
    modalIsOpen = _useState2[0],
    setModalIsOpen = _useState2[1];
  var handleClickOpenModal = function handleClickOpenModal() {
    setModalIsOpen(true);
  };
  var handleClickCloseModal = function handleClickCloseModal() {
    setModalIsOpen(false);
  };
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("div", {
    id: "taskItem",
    onClick: handleClickOpenModal
  }, /*#__PURE__*/React.createElement("div", {
    className: "taskItemElements"
  }, /*#__PURE__*/React.createElement("p", null, title))), /*#__PURE__*/React.createElement(_reactModal.default, {
    isOpen: modalIsOpen,
    onRequestClose: handleClickCloseModal,
    style: _customStyleModal.customStyles
  }, /*#__PURE__*/React.createElement(_MyModalTaskItem.MyModalTaskItem, {
    id: id,
    name: name,
    description: description,
    status: status,
    type: type,
    exp: exp,
    money: money,
    remove: remove,
    closeModal: handleClickCloseModal
  })));
};
var _default = exports.default = TaskItem;