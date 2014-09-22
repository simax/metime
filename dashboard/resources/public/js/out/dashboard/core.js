// Compiled by ClojureScript 0.0-2342
goog.provide('dashboard.core');
goog.require('cljs.core');
goog.require('cljs.core.async');
goog.require('cljs_http.client');
goog.require('cljs_http.client');
goog.require('om.dom');
goog.require('om.dom');
goog.require('om.core');
goog.require('om.core');
goog.require('cljs.core.async');
goog.require('goog.events');
goog.require('goog.events');
cljs.core.enable_console_print_BANG_.call(null);
cljs.core.println.call(null,"Hello world!");
dashboard.core.app_state = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
dashboard.core.fetch_departments = (function fetch_departments(url){var c = cljs.core.async.chan.call(null);var c__6424__auto___10121 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6424__auto___10121,c){
return (function (){var f__6425__auto__ = (function (){var switch__6409__auto__ = ((function (c__6424__auto___10121,c){
return (function (state_10112){var state_val_10113 = (state_10112[(1)]);if((state_val_10113 === (2)))
{var inst_10110 = (state_10112[(2)]);var state_10112__$1 = state_10112;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_10112__$1,inst_10110);
} else
{if((state_val_10113 === (1)))
{var inst_10099 = cljs.core.PersistentVector.EMPTY_NODE;var inst_10100 = [new cljs.core.Keyword(null,"department","department",-359157087)];var inst_10101 = ["Department 1"];var inst_10102 = cljs.core.PersistentHashMap.fromArrays(inst_10100,inst_10101);var inst_10103 = [new cljs.core.Keyword(null,"department","department",-359157087)];var inst_10104 = ["Department 2"];var inst_10105 = cljs.core.PersistentHashMap.fromArrays(inst_10103,inst_10104);var inst_10106 = [inst_10102,inst_10105];var inst_10107 = (new cljs.core.PersistentVector(null,2,(5),inst_10099,inst_10106,null));var inst_10108 = cljs.core.vec.call(null,inst_10107);var state_10112__$1 = state_10112;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_10112__$1,(2),c,inst_10108);
} else
{return null;
}
}
});})(c__6424__auto___10121,c))
;return ((function (switch__6409__auto__,c__6424__auto___10121,c){
return (function() {
var state_machine__6410__auto__ = null;
var state_machine__6410__auto____0 = (function (){var statearr_10117 = [null,null,null,null,null,null,null];(statearr_10117[(0)] = state_machine__6410__auto__);
(statearr_10117[(1)] = (1));
return statearr_10117;
});
var state_machine__6410__auto____1 = (function (state_10112){while(true){
var ret_value__6411__auto__ = (function (){try{while(true){
var result__6412__auto__ = switch__6409__auto__.call(null,state_10112);if(cljs.core.keyword_identical_QMARK_.call(null,result__6412__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6412__auto__;
}
break;
}
}catch (e10118){if((e10118 instanceof Object))
{var ex__6413__auto__ = e10118;var statearr_10119_10122 = state_10112;(statearr_10119_10122[(5)] = ex__6413__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_10112);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e10118;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6411__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__10123 = state_10112;
state_10112 = G__10123;
continue;
}
} else
{return ret_value__6411__auto__;
}
break;
}
});
state_machine__6410__auto__ = function(state_10112){
switch(arguments.length){
case 0:
return state_machine__6410__auto____0.call(this);
case 1:
return state_machine__6410__auto____1.call(this,state_10112);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6410__auto____0;
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6410__auto____1;
return state_machine__6410__auto__;
})()
;})(switch__6409__auto__,c__6424__auto___10121,c))
})();var state__6426__auto__ = (function (){var statearr_10120 = f__6425__auto__.call(null);(statearr_10120[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6424__auto___10121);
return statearr_10120;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6426__auto__);
});})(c__6424__auto___10121,c))
);
return c;
});
dashboard.core.department_name = (function department_name(p__10124,owner,opts){var map__10129 = p__10124;var map__10129__$1 = ((cljs.core.seq_QMARK_.call(null,map__10129))?cljs.core.apply.call(null,cljs.core.hash_map,map__10129):map__10129);var department = cljs.core.get.call(null,map__10129__$1,new cljs.core.Keyword(null,"department","department",-359157087));if(typeof dashboard.core.t10130 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t10130 = (function (department,map__10129,opts,owner,p__10124,department_name,meta10131){
this.department = department;
this.map__10129 = map__10129;
this.opts = opts;
this.owner = owner;
this.p__10124 = p__10124;
this.department_name = department_name;
this.meta10131 = meta10131;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t10130.cljs$lang$type = true;
dashboard.core.t10130.cljs$lang$ctorStr = "dashboard.core/t10130";
dashboard.core.t10130.cljs$lang$ctorPrWriter = ((function (map__10129,map__10129__$1,department){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t10130");
});})(map__10129,map__10129__$1,department))
;
dashboard.core.t10130.prototype.om$core$IRender$ = true;
dashboard.core.t10130.prototype.om$core$IRender$render$arity$1 = ((function (map__10129,map__10129__$1,department){
return (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return React.DOM.li(null,React.DOM.span(null,"hello !!!!!"));
});})(map__10129,map__10129__$1,department))
;
dashboard.core.t10130.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (map__10129,map__10129__$1,department){
return (function (_10132){var self__ = this;
var _10132__$1 = this;return self__.meta10131;
});})(map__10129,map__10129__$1,department))
;
dashboard.core.t10130.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (map__10129,map__10129__$1,department){
return (function (_10132,meta10131__$1){var self__ = this;
var _10132__$1 = this;return (new dashboard.core.t10130(self__.department,self__.map__10129,self__.opts,self__.owner,self__.p__10124,self__.department_name,meta10131__$1));
});})(map__10129,map__10129__$1,department))
;
dashboard.core.__GT_t10130 = ((function (map__10129,map__10129__$1,department){
return (function __GT_t10130(department__$1,map__10129__$2,opts__$1,owner__$1,p__10124__$1,department_name__$1,meta10131){return (new dashboard.core.t10130(department__$1,map__10129__$2,opts__$1,owner__$1,p__10124__$1,department_name__$1,meta10131));
});})(map__10129,map__10129__$1,department))
;
}
return (new dashboard.core.t10130(department,map__10129__$1,opts,owner,p__10124,department_name,null));
});
dashboard.core.department_list = (function department_list(p__10133){var map__10138 = p__10133;var map__10138__$1 = ((cljs.core.seq_QMARK_.call(null,map__10138))?cljs.core.apply.call(null,cljs.core.hash_map,map__10138):map__10138);var departments = cljs.core.get.call(null,map__10138__$1,new cljs.core.Keyword(null,"departments","departments",799278298));console.log(cljs.core.println.call(null,"list of departments:",departments));
if(typeof dashboard.core.t10139 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t10139 = (function (departments,map__10138,p__10133,department_list,meta10140){
this.departments = departments;
this.map__10138 = map__10138;
this.p__10133 = p__10133;
this.department_list = department_list;
this.meta10140 = meta10140;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t10139.cljs$lang$type = true;
dashboard.core.t10139.cljs$lang$ctorStr = "dashboard.core/t10139";
dashboard.core.t10139.cljs$lang$ctorPrWriter = ((function (map__10138,map__10138__$1,departments){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t10139");
});})(map__10138,map__10138__$1,departments))
;
dashboard.core.t10139.prototype.om$core$IRender$ = true;
dashboard.core.t10139.prototype.om$core$IRender$render$arity$1 = ((function (map__10138,map__10138__$1,departments){
return (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return cljs.core.apply.call(null,om.dom.ul,null,om.core.build_all.call(null,dashboard.core.department_name,self__.departments));
});})(map__10138,map__10138__$1,departments))
;
dashboard.core.t10139.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (map__10138,map__10138__$1,departments){
return (function (_10141){var self__ = this;
var _10141__$1 = this;return self__.meta10140;
});})(map__10138,map__10138__$1,departments))
;
dashboard.core.t10139.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (map__10138,map__10138__$1,departments){
return (function (_10141,meta10140__$1){var self__ = this;
var _10141__$1 = this;return (new dashboard.core.t10139(self__.departments,self__.map__10138,self__.p__10133,self__.department_list,meta10140__$1));
});})(map__10138,map__10138__$1,departments))
;
dashboard.core.__GT_t10139 = ((function (map__10138,map__10138__$1,departments){
return (function __GT_t10139(departments__$1,map__10138__$2,p__10133__$1,department_list__$1,meta10140){return (new dashboard.core.t10139(departments__$1,map__10138__$2,p__10133__$1,department_list__$1,meta10140));
});})(map__10138,map__10138__$1,departments))
;
}
return (new dashboard.core.t10139(departments,map__10138__$1,p__10133,department_list,null));
});
dashboard.core.departments_box = (function departments_box(app,owner,opts){if(typeof dashboard.core.t10148 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t10148 = (function (opts,owner,app,departments_box,meta10149){
this.opts = opts;
this.owner = owner;
this.app = app;
this.departments_box = departments_box;
this.meta10149 = meta10149;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t10148.cljs$lang$type = true;
dashboard.core.t10148.cljs$lang$ctorStr = "dashboard.core/t10148";
dashboard.core.t10148.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t10148");
});
dashboard.core.t10148.prototype.om$core$IRenderState$ = true;
dashboard.core.t10148.prototype.om$core$IRenderState$render_state$arity$2 = (function (_,p__10151){var self__ = this;
var map__10152 = p__10151;var map__10152__$1 = ((cljs.core.seq_QMARK_.call(null,map__10152))?cljs.core.apply.call(null,cljs.core.hash_map,map__10152):map__10152);var departments = cljs.core.get.call(null,map__10152__$1,new cljs.core.Keyword(null,"departments","departments",799278298));var ___$1 = this;console.log(cljs.core.println.call(null,"departments in app:",departments));
React.DOM.h1(null,"Departments");
return om.core.build.call(null,dashboard.core.department_list,self__.app);
});
dashboard.core.t10148.prototype.om$core$IInitState$ = true;
dashboard.core.t10148.prototype.om$core$IInitState$init_state$arity$1 = (function (_){var self__ = this;
var ___$1 = this;var departments = cljs.core.async._LT__BANG_.call(null,dashboard.core.fetch_departments.call(null,new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(self__.opts)));console.log(cljs.core.println.call(null,"xx:",departments));
om.core.transact_BANG_.call(null,self__.app,new cljs.core.Keyword(null,"departments","departments",799278298),((function (departments,___$1){
return (function (p1__10142_SHARP_){return cljs.core.conj.call(null,p1__10142_SHARP_,departments);
});})(departments,___$1))
);
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"departments","departments",799278298),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"department","department",-359157087),"Department 1"], null),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"department","department",-359157087),"Department 2"], null)], null)], null);
});
dashboard.core.t10148.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_10150){var self__ = this;
var _10150__$1 = this;return self__.meta10149;
});
dashboard.core.t10148.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_10150,meta10149__$1){var self__ = this;
var _10150__$1 = this;return (new dashboard.core.t10148(self__.opts,self__.owner,self__.app,self__.departments_box,meta10149__$1));
});
dashboard.core.__GT_t10148 = (function __GT_t10148(opts__$1,owner__$1,app__$1,departments_box__$1,meta10149){return (new dashboard.core.t10148(opts__$1,owner__$1,app__$1,departments_box__$1,meta10149));
});
}
return (new dashboard.core.t10148(opts,owner,app,departments_box,null));
});
dashboard.core.om_app = (function om_app(app,owner){if(typeof dashboard.core.t10156 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t10156 = (function (owner,app,om_app,meta10157){
this.owner = owner;
this.app = app;
this.om_app = om_app;
this.meta10157 = meta10157;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t10156.cljs$lang$type = true;
dashboard.core.t10156.cljs$lang$ctorStr = "dashboard.core/t10156";
dashboard.core.t10156.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t10156");
});
dashboard.core.t10156.prototype.om$core$IRender$ = true;
dashboard.core.t10156.prototype.om$core$IRender$render$arity$1 = (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return React.DOM.div(null,om.core.build.call(null,dashboard.core.departments_box,self__.app,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"opts","opts",155075701),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"url","url",276297046),"http://localhost:3030/api/departments",new cljs.core.Keyword(null,"poll-interval","poll-interval",345867570),(2000)], null)], null)));
});
dashboard.core.t10156.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_10158){var self__ = this;
var _10158__$1 = this;return self__.meta10157;
});
dashboard.core.t10156.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_10158,meta10157__$1){var self__ = this;
var _10158__$1 = this;return (new dashboard.core.t10156(self__.owner,self__.app,self__.om_app,meta10157__$1));
});
dashboard.core.__GT_t10156 = (function __GT_t10156(owner__$1,app__$1,om_app__$1,meta10157){return (new dashboard.core.t10156(owner__$1,app__$1,om_app__$1,meta10157));
});
}
return (new dashboard.core.t10156(owner,app,om_app,null));
});
om.core.root.call(null,dashboard.core.om_app,dashboard.core.app_state,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"target","target",253001721),document.getElementById("main-container")], null));

//# sourceMappingURL=core.js.map