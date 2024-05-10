"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.ClanList = void 0;
var _react = require("react");
var _Clan = require("../../Clan/app/Clan");
require("../styles/ClanListstyles.css");
var _io = require("react-icons/io5");
function _slicedToArray(arr, i) { return _arrayWithHoles(arr) || _iterableToArrayLimit(arr, i) || _unsupportedIterableToArray(arr, i) || _nonIterableRest(); }
function _nonIterableRest() { throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }
function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }
function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) arr2[i] = arr[i]; return arr2; }
function _iterableToArrayLimit(r, l) { var t = null == r ? null : "undefined" != typeof Symbol && r[Symbol.iterator] || r["@@iterator"]; if (null != t) { var e, n, i, u, a = [], f = !0, o = !1; try { if (i = (t = t.call(r)).next, 0 === l) { if (Object(t) !== t) return; f = !1; } else for (; !(f = (e = i.call(t)).done) && (a.push(e.value), a.length !== l); f = !0); } catch (r) { o = !0, n = r; } finally { try { if (!f && null != t.return && (u = t.return(), Object(u) !== u)) return; } finally { if (o) throw n; } } return a; } }
function _arrayWithHoles(arr) { if (Array.isArray(arr)) return arr; }
var ClanList = exports.ClanList = function ClanList() {
  var _useState = (0, _react.useState)({
      children: [{
        name: "хуй"
      }, {
        name: "хуй"
      }, {
        name: "пизда"
      }, {
        name: "дорожный знак"
      }, {
        name: "learn"
      }, {
        name: "Хуй"
      }, {
        name: "абабабабаба"
      }]
    }),
    _useState2 = _slicedToArray(_useState, 1),
    clans = _useState2[0];
  var _useState3 = (0, _react.useState)(clans),
    _useState4 = _slicedToArray(_useState3, 2),
    filteredClans = _useState4[0],
    setFilteredClans = _useState4[1];
  var FilterText = function FilterText(filteredData) {
    return filteredData.map(function (item) {
      if (item.name.length > 7) {
        return {
          name: item.name.slice(0, 5) + "..."
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
  return /*#__PURE__*/React.createElement("div", {
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
  })), /*#__PURE__*/React.createElement("div", {
    id: "clansContainer"
  }, FilterText(filteredClans.children).map(function (item, index) {
    return /*#__PURE__*/React.createElement(_Clan.Clan, {
      key: index,
      name: item.name
    });
  }))));
};