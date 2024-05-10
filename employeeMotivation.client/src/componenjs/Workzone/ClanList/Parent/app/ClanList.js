"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.ClanList = void 0;
var _react = require("react");
var _Clan = require("../../Clan/app/Clan");
require("../styles/ClanListstyles.css");
var _io = require("react-icons/io5");
var _reactModal = _interopRequireDefault(require("react-modal"));
var _MyModal = require("../../../../MyModal/Modal/app/MyModal");
var _Items = require("../../../../../entities/Items.interface");
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
function _slicedToArray(arr, i) { return _arrayWithHoles(arr) || _iterableToArrayLimit(arr, i) || _unsupportedIterableToArray(arr, i) || _nonIterableRest(); }
function _nonIterableRest() { throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }
function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }
function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) arr2[i] = arr[i]; return arr2; }
function _iterableToArrayLimit(r, l) { var t = null == r ? null : "undefined" != typeof Symbol && r[Symbol.iterator] || r["@@iterator"]; if (null != t) { var e, n, i, u, a = [], f = !0, o = !1; try { if (i = (t = t.call(r)).next, 0 === l) { if (Object(t) !== t) return; f = !1; } else for (; !(f = (e = i.call(t)).done) && (a.push(e.value), a.length !== l); f = !0); } catch (r) { o = !0, n = r; } finally { try { if (!f && null != t.return && (u = t.return(), Object(u) !== u)) return; } finally { if (o) throw n; } } return a; } }
function _arrayWithHoles(arr) { if (Array.isArray(arr)) return arr; }
var ClanList = exports.ClanList = function ClanList() {
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
      transition: 'background-color 0.5s ease' // добавляем анимацию изменения цвета фона
    }
  };
  var _useState = (0, _react.useState)({
      children: [{
        id: 0,
        name: 'Красивые'
      }, {
        id: 1,
        name: 'Cool Boys'
      }, {
        id: 2,
        name: 'Фанаты Полуяна'
      }]
    }),
    _useState2 = _slicedToArray(_useState, 1),
    clans = _useState2[0];
  var _useState3 = (0, _react.useState)(false),
    _useState4 = _slicedToArray(_useState3, 2),
    modalIsOpen = _useState4[0],
    setModalIsOpen = _useState4[1];
  var openModal = function openModal() {
    setModalIsOpen(true);
  };
  var closeModal = function closeModal() {
    setModalIsOpen(false);
  };
  var _useState5 = (0, _react.useState)(clans),
    _useState6 = _slicedToArray(_useState5, 2),
    filteredClans = _useState6[0],
    setFilteredClans = _useState6[1];
  var FilterText = function FilterText(filteredData) {
    return filteredData.map(function (item) {
      if (item.name.length > 42) {
        return {
          name: item.name.slice(0, 35) + "..."
        };
      } else {
        return item;
      }
    });
  };
  var SearchClan = function SearchClan(event) {
    var searchTerm = event.target.value;
    var filteredData = clans.children.filter(function (item) {
      return item.name.toLowerCase().includes(searchTerm.toLowerCase());
    });
    setFilteredClans({
      children: filteredData
    });
  };
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("div", {
    className: "mainView"
  }, /*#__PURE__*/React.createElement("div", {
    className: "clanListMainView"
  }, /*#__PURE__*/React.createElement("div", {
    className: "searchConatiner"
  }, /*#__PURE__*/React.createElement("div", {
    className: "searchIcon"
  }, /*#__PURE__*/React.createElement(_io.IoSearchOutline, null)), /*#__PURE__*/React.createElement("div", null, ">"), /*#__PURE__*/React.createElement("input", {
    type: "text",
    onChange: SearchClan
  })), /*#__PURE__*/React.createElement("button", {
    onClick: openModal
  }, "Add clan"), /*#__PURE__*/React.createElement("div", {
    id: "clansContainer"
  }, FilterText(filteredClans.children).map(function (item, index) {
    return /*#__PURE__*/React.createElement(_Clan.Clan, {
      id: index,
      key: index,
      name: item.name
    });
  })))), /*#__PURE__*/React.createElement(_reactModal.default, {
    isOpen: modalIsOpen,
    onRequestClose: closeModal,
    style: customStyles
  }, /*#__PURE__*/React.createElement(_MyModal.MyModal, {
    inputValue: "Enter clan name"
  })));
};