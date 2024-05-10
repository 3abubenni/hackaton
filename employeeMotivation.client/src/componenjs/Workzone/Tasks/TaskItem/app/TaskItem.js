"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;
var _react = require("react");
var _Items = require("../../../../../entities/Items.interface");
require("../styles/TaskItemstyles.css");
var _reactModal = _interopRequireDefault(require("react-modal"));
var _MyModalItems = require("../../../../MyModal/ModalItems/app/MyModalItems");
var _reactRouterDom = require("react-router-dom");
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
function _slicedToArray(arr, i) { return _arrayWithHoles(arr) || _iterableToArrayLimit(arr, i) || _unsupportedIterableToArray(arr, i) || _nonIterableRest(); }
function _nonIterableRest() { throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }
function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }
function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) arr2[i] = arr[i]; return arr2; }
function _iterableToArrayLimit(r, l) { var t = null == r ? null : "undefined" != typeof Symbol && r[Symbol.iterator] || r["@@iterator"]; if (null != t) { var e, n, i, u, a = [], f = !0, o = !1; try { if (i = (t = t.call(r)).next, 0 === l) { if (Object(t) !== t) return; f = !1; } else for (; !(f = (e = i.call(t)).done) && (a.push(e.value), a.length !== l); f = !0); } catch (r) { o = !0, n = r; } finally { try { if (!f && null != t.return && (u = t.return(), Object(u) !== u)) return; } finally { if (o) throw n; } } return a; } }
function _arrayWithHoles(arr) { if (Array.isArray(arr)) return arr; }
var TaskItem = function TaskItem(_ref) {
  var _url_now$;
  var id = _ref.id,
    title = _ref.title,
    description = _ref.description,
    exp = _ref.exp,
    money = _ref.money,
    remove = _ref.remove;
  var customStyles = {
    content: {
      top: '50%',
      left: '50%',
      right: 'auto',
      bottom: 'auto',
      marginRight: '-50%',
      transform: 'translate(-50%, -50%)',
      backgroundColor: 'rgb(22, 62, 73)',
      borderRadius: '20px'
    },
    overlay: {
      backgroundColor: 'rgba(0, 0, 0, 0.5)',
      transition: 'background-color 0.5s ease'
    }
  };
  var url_now = (0, _reactRouterDom.useParams)();
  var url_from = (_url_now$ = url_now['*']) === null || _url_now$ === void 0 ? void 0 : _url_now$.split("/")[1];
  var _useState = (0, _react.useState)(false),
    _useState2 = _slicedToArray(_useState, 2),
    modalIsOpen = _useState2[0],
    setModalIsOpen = _useState2[1];
  var openModal = function openModal() {
    setModalIsOpen(true);
  };
  var closeModal = function closeModal() {
    setModalIsOpen(false);
  };
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("div", {
    id: "taskItem",
    onClick: openModal
  }, /*#__PURE__*/React.createElement("div", {
    className: "taskItemElements"
  }, /*#__PURE__*/React.createElement("p", null, title))), /*#__PURE__*/React.createElement(_reactModal.default, {
    isOpen: modalIsOpen,
    onRequestClose: closeModal,
    style: customStyles
  }, /*#__PURE__*/React.createElement(_MyModalItems.MyModalItems, {
    id: id,
    name: title,
    description: description,
    type: url_from == 'usersTasks' ? 'answer' : 'task',
    exp: exp,
    money: money,
    remove: remove,
    closeModal: closeModal
  })));
};
var _default = exports.default = TaskItem;