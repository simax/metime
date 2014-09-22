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
dashboard.core.app_state = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
dashboard.core.fetch_departments = (function fetch_departments(url){var c = cljs.core.async.chan.call(null);var c__6424__auto___12896 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6424__auto___12896,c){
return (function (){var f__6425__auto__ = (function (){var switch__6409__auto__ = ((function (c__6424__auto___12896,c){
return (function (state_12886){var state_val_12887 = (state_12886[(1)]);if((state_val_12887 === (2)))
{var inst_12880 = (state_12886[(7)]);var inst_12883 = (state_12886[(2)]);var inst_12884 = console.log(inst_12880,inst_12883);var state_12886__$1 = state_12886;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12886__$1,inst_12884);
} else
{if((state_val_12887 === (1)))
{var inst_12871 = cljs.core.PersistentVector.EMPTY_NODE;var inst_12872 = [new cljs.core.Keyword(null,"department","department",-359157087)];var inst_12873 = ["Department 1"];var inst_12874 = cljs.core.PersistentHashMap.fromArrays(inst_12872,inst_12873);var inst_12875 = [new cljs.core.Keyword(null,"department","department",-359157087)];var inst_12876 = ["Department 2"];var inst_12877 = cljs.core.PersistentHashMap.fromArrays(inst_12875,inst_12876);var inst_12878 = [inst_12874,inst_12877];var inst_12879 = (new cljs.core.PersistentVector(null,2,(5),inst_12871,inst_12878,null));var inst_12880 = cljs.core.println.call(null,"fetch-departments:",inst_12879);var inst_12881 = cljs.core.vec.call(null,inst_12879);var state_12886__$1 = (function (){var statearr_12888 = state_12886;(statearr_12888[(7)] = inst_12880);
return statearr_12888;
})();return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12886__$1,(2),c,inst_12881);
} else
{return null;
}
}
});})(c__6424__auto___12896,c))
;return ((function (switch__6409__auto__,c__6424__auto___12896,c){
return (function() {
var state_machine__6410__auto__ = null;
var state_machine__6410__auto____0 = (function (){var statearr_12892 = [null,null,null,null,null,null,null,null];(statearr_12892[(0)] = state_machine__6410__auto__);
(statearr_12892[(1)] = (1));
return statearr_12892;
});
var state_machine__6410__auto____1 = (function (state_12886){while(true){
var ret_value__6411__auto__ = (function (){try{while(true){
var result__6412__auto__ = switch__6409__auto__.call(null,state_12886);if(cljs.core.keyword_identical_QMARK_.call(null,result__6412__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6412__auto__;
}
break;
}
}catch (e12893){if((e12893 instanceof Object))
{var ex__6413__auto__ = e12893;var statearr_12894_12897 = state_12886;(statearr_12894_12897[(5)] = ex__6413__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12886);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12893;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6411__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12898 = state_12886;
state_12886 = G__12898;
continue;
}
} else
{return ret_value__6411__auto__;
}
break;
}
});
state_machine__6410__auto__ = function(state_12886){
switch(arguments.length){
case 0:
return state_machine__6410__auto____0.call(this);
case 1:
return state_machine__6410__auto____1.call(this,state_12886);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6410__auto____0;
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6410__auto____1;
return state_machine__6410__auto__;
})()
;})(switch__6409__auto__,c__6424__auto___12896,c))
})();var state__6426__auto__ = (function (){var statearr_12895 = f__6425__auto__.call(null);(statearr_12895[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6424__auto___12896);
return statearr_12895;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6426__auto__);
});})(c__6424__auto___12896,c))
);
return c;
});
dashboard.core.department_name = (function department_name(p__12899,owner,opts){var map__12904 = p__12899;var map__12904__$1 = ((cljs.core.seq_QMARK_.call(null,map__12904))?cljs.core.apply.call(null,cljs.core.hash_map,map__12904):map__12904);var department = cljs.core.get.call(null,map__12904__$1,new cljs.core.Keyword(null,"department","department",-359157087));if(typeof dashboard.core.t12905 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t12905 = (function (department,map__12904,opts,owner,p__12899,department_name,meta12906){
this.department = department;
this.map__12904 = map__12904;
this.opts = opts;
this.owner = owner;
this.p__12899 = p__12899;
this.department_name = department_name;
this.meta12906 = meta12906;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t12905.cljs$lang$type = true;
dashboard.core.t12905.cljs$lang$ctorStr = "dashboard.core/t12905";
dashboard.core.t12905.cljs$lang$ctorPrWriter = ((function (map__12904,map__12904__$1,department){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t12905");
});})(map__12904,map__12904__$1,department))
;
dashboard.core.t12905.prototype.om$core$IRender$ = true;
dashboard.core.t12905.prototype.om$core$IRender$render$arity$1 = ((function (map__12904,map__12904__$1,department){
return (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return React.DOM.li(null,React.DOM.span(null,self__.department));
});})(map__12904,map__12904__$1,department))
;
dashboard.core.t12905.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (map__12904,map__12904__$1,department){
return (function (_12907){var self__ = this;
var _12907__$1 = this;return self__.meta12906;
});})(map__12904,map__12904__$1,department))
;
dashboard.core.t12905.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (map__12904,map__12904__$1,department){
return (function (_12907,meta12906__$1){var self__ = this;
var _12907__$1 = this;return (new dashboard.core.t12905(self__.department,self__.map__12904,self__.opts,self__.owner,self__.p__12899,self__.department_name,meta12906__$1));
});})(map__12904,map__12904__$1,department))
;
dashboard.core.__GT_t12905 = ((function (map__12904,map__12904__$1,department){
return (function __GT_t12905(department__$1,map__12904__$2,opts__$1,owner__$1,p__12899__$1,department_name__$1,meta12906){return (new dashboard.core.t12905(department__$1,map__12904__$2,opts__$1,owner__$1,p__12899__$1,department_name__$1,meta12906));
});})(map__12904,map__12904__$1,department))
;
}
return (new dashboard.core.t12905(department,map__12904__$1,opts,owner,p__12899,department_name,null));
});
dashboard.core.department_list = (function department_list(p__12908){var map__12913 = p__12908;var map__12913__$1 = ((cljs.core.seq_QMARK_.call(null,map__12913))?cljs.core.apply.call(null,cljs.core.hash_map,map__12913):map__12913);var departments = cljs.core.get.call(null,map__12913__$1,new cljs.core.Keyword(null,"departments","departments",799278298));console.log(cljs.core.println.call(null,"department-list:",departments));
if(typeof dashboard.core.t12914 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t12914 = (function (departments,map__12913,p__12908,department_list,meta12915){
this.departments = departments;
this.map__12913 = map__12913;
this.p__12908 = p__12908;
this.department_list = department_list;
this.meta12915 = meta12915;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t12914.cljs$lang$type = true;
dashboard.core.t12914.cljs$lang$ctorStr = "dashboard.core/t12914";
dashboard.core.t12914.cljs$lang$ctorPrWriter = ((function (map__12913,map__12913__$1,departments){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t12914");
});})(map__12913,map__12913__$1,departments))
;
dashboard.core.t12914.prototype.om$core$IRender$ = true;
dashboard.core.t12914.prototype.om$core$IRender$render$arity$1 = ((function (map__12913,map__12913__$1,departments){
return (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return cljs.core.apply.call(null,om.dom.ul,null,om.core.build_all.call(null,dashboard.core.department_name,cljs.core.first.call(null,self__.departments)));
});})(map__12913,map__12913__$1,departments))
;
dashboard.core.t12914.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (map__12913,map__12913__$1,departments){
return (function (_12916){var self__ = this;
var _12916__$1 = this;return self__.meta12915;
});})(map__12913,map__12913__$1,departments))
;
dashboard.core.t12914.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (map__12913,map__12913__$1,departments){
return (function (_12916,meta12915__$1){var self__ = this;
var _12916__$1 = this;return (new dashboard.core.t12914(self__.departments,self__.map__12913,self__.p__12908,self__.department_list,meta12915__$1));
});})(map__12913,map__12913__$1,departments))
;
dashboard.core.__GT_t12914 = ((function (map__12913,map__12913__$1,departments){
return (function __GT_t12914(departments__$1,map__12913__$2,p__12908__$1,department_list__$1,meta12915){return (new dashboard.core.t12914(departments__$1,map__12913__$2,p__12908__$1,department_list__$1,meta12915));
});})(map__12913,map__12913__$1,departments))
;
}
return (new dashboard.core.t12914(departments,map__12913__$1,p__12908,department_list,null));
});
dashboard.core.departments_box = (function departments_box(app,owner,opts){if(typeof dashboard.core.t12946 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t12946 = (function (opts,owner,app,departments_box,meta12947){
this.opts = opts;
this.owner = owner;
this.app = app;
this.departments_box = departments_box;
this.meta12947 = meta12947;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t12946.cljs$lang$type = true;
dashboard.core.t12946.cljs$lang$ctorStr = "dashboard.core/t12946";
dashboard.core.t12946.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t12946");
});
dashboard.core.t12946.prototype.om$core$IRenderState$ = true;
dashboard.core.t12946.prototype.om$core$IRenderState$render_state$arity$2 = (function (_,p__12949){var self__ = this;
var map__12950 = p__12949;var map__12950__$1 = ((cljs.core.seq_QMARK_.call(null,map__12950))?cljs.core.apply.call(null,cljs.core.hash_map,map__12950):map__12950);var departments = cljs.core.get.call(null,map__12950__$1,new cljs.core.Keyword(null,"departments","departments",799278298));var ___$1 = this;console.log(cljs.core.println.call(null,"Render-state:",departments));
React.DOM.h1(null,"Departments");
return om.core.build.call(null,dashboard.core.department_list,self__.app);
});
dashboard.core.t12946.prototype.om$core$IWillMount$ = true;
dashboard.core.t12946.prototype.om$core$IWillMount$will_mount$arity$1 = (function (_){var self__ = this;
var ___$1 = this;var c__6424__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6424__auto__,___$1){
return (function (){var f__6425__auto__ = (function (){var switch__6409__auto__ = ((function (c__6424__auto__,___$1){
return (function (state_12964){var state_val_12965 = (state_12964[(1)]);if((state_val_12965 === (2)))
{var inst_12954 = (state_12964[(2)]);var inst_12955 = cljs.core.println.call(null,"Will-mount-1:",inst_12954);var inst_12956 = console.log(inst_12955);var inst_12957 = (function (){var deps = inst_12954;return ((function (deps,inst_12954,inst_12955,inst_12956,state_val_12965,c__6424__auto__,___$1){
return (function (p1__12917_SHARP_){return cljs.core.conj.call(null,p1__12917_SHARP_,deps);
});
;})(deps,inst_12954,inst_12955,inst_12956,state_val_12965,c__6424__auto__,___$1))
})();var inst_12958 = om.core.transact_BANG_.call(null,self__.app,new cljs.core.Keyword(null,"departments","departments",799278298),inst_12957);var inst_12959 = cljs.core.deref.call(null,self__.app);var inst_12960 = new cljs.core.Keyword(null,"departments","departments",799278298).cljs$core$IFn$_invoke$arity$1(inst_12959);var inst_12961 = cljs.core.println.call(null,"Will-mount-2:",inst_12960);var inst_12962 = console.log(inst_12961);var state_12964__$1 = (function (){var statearr_12966 = state_12964;(statearr_12966[(7)] = inst_12956);
(statearr_12966[(8)] = inst_12958);
return statearr_12966;
})();return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12964__$1,inst_12962);
} else
{if((state_val_12965 === (1)))
{var inst_12951 = new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(self__.opts);var inst_12952 = dashboard.core.fetch_departments.call(null,inst_12951);var state_12964__$1 = state_12964;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12964__$1,(2),inst_12952);
} else
{return null;
}
}
});})(c__6424__auto__,___$1))
;return ((function (switch__6409__auto__,c__6424__auto__,___$1){
return (function() {
var state_machine__6410__auto__ = null;
var state_machine__6410__auto____0 = (function (){var statearr_12970 = [null,null,null,null,null,null,null,null,null];(statearr_12970[(0)] = state_machine__6410__auto__);
(statearr_12970[(1)] = (1));
return statearr_12970;
});
var state_machine__6410__auto____1 = (function (state_12964){while(true){
var ret_value__6411__auto__ = (function (){try{while(true){
var result__6412__auto__ = switch__6409__auto__.call(null,state_12964);if(cljs.core.keyword_identical_QMARK_.call(null,result__6412__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6412__auto__;
}
break;
}
}catch (e12971){if((e12971 instanceof Object))
{var ex__6413__auto__ = e12971;var statearr_12972_12974 = state_12964;(statearr_12972_12974[(5)] = ex__6413__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12964);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12971;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6411__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12975 = state_12964;
state_12964 = G__12975;
continue;
}
} else
{return ret_value__6411__auto__;
}
break;
}
});
state_machine__6410__auto__ = function(state_12964){
switch(arguments.length){
case 0:
return state_machine__6410__auto____0.call(this);
case 1:
return state_machine__6410__auto____1.call(this,state_12964);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6410__auto____0;
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6410__auto____1;
return state_machine__6410__auto__;
})()
;})(switch__6409__auto__,c__6424__auto__,___$1))
})();var state__6426__auto__ = (function (){var statearr_12973 = f__6425__auto__.call(null);(statearr_12973[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6424__auto__);
return statearr_12973;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6426__auto__);
});})(c__6424__auto__,___$1))
);
return c__6424__auto__;
});
dashboard.core.t12946.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_12948){var self__ = this;
var _12948__$1 = this;return self__.meta12947;
});
dashboard.core.t12946.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_12948,meta12947__$1){var self__ = this;
var _12948__$1 = this;return (new dashboard.core.t12946(self__.opts,self__.owner,self__.app,self__.departments_box,meta12947__$1));
});
dashboard.core.__GT_t12946 = (function __GT_t12946(opts__$1,owner__$1,app__$1,departments_box__$1,meta12947){return (new dashboard.core.t12946(opts__$1,owner__$1,app__$1,departments_box__$1,meta12947));
});
}
return (new dashboard.core.t12946(opts,owner,app,departments_box,null));
});
dashboard.core.om_app = (function om_app(app,owner){if(typeof dashboard.core.t12979 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t12979 = (function (owner,app,om_app,meta12980){
this.owner = owner;
this.app = app;
this.om_app = om_app;
this.meta12980 = meta12980;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t12979.cljs$lang$type = true;
dashboard.core.t12979.cljs$lang$ctorStr = "dashboard.core/t12979";
dashboard.core.t12979.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t12979");
});
dashboard.core.t12979.prototype.om$core$IRender$ = true;
dashboard.core.t12979.prototype.om$core$IRender$render$arity$1 = (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return React.DOM.div(null,om.core.build.call(null,dashboard.core.departments_box,self__.app,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"opts","opts",155075701),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"url","url",276297046),"http://localhost:3030/api/departments",new cljs.core.Keyword(null,"poll-interval","poll-interval",345867570),(2000)], null)], null)));
});
dashboard.core.t12979.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_12981){var self__ = this;
var _12981__$1 = this;return self__.meta12980;
});
dashboard.core.t12979.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_12981,meta12980__$1){var self__ = this;
var _12981__$1 = this;return (new dashboard.core.t12979(self__.owner,self__.app,self__.om_app,meta12980__$1));
});
dashboard.core.__GT_t12979 = (function __GT_t12979(owner__$1,app__$1,om_app__$1,meta12980){return (new dashboard.core.t12979(owner__$1,app__$1,om_app__$1,meta12980));
});
}
return (new dashboard.core.t12979(owner,app,om_app,null));
});
om.core.root.call(null,dashboard.core.om_app,dashboard.core.app_state,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"target","target",253001721),document.getElementById("main-container")], null));

//# sourceMappingURL=core.js.map