// Compiled by ClojureScript 0.0-2342
goog.provide('cljs.core.async');
goog.require('cljs.core');
goog.require('cljs.core.async.impl.channels');
goog.require('cljs.core.async.impl.dispatch');
goog.require('cljs.core.async.impl.ioc_helpers');
goog.require('cljs.core.async.impl.protocols');
goog.require('cljs.core.async.impl.channels');
goog.require('cljs.core.async.impl.buffers');
goog.require('cljs.core.async.impl.protocols');
goog.require('cljs.core.async.impl.timers');
goog.require('cljs.core.async.impl.dispatch');
goog.require('cljs.core.async.impl.ioc_helpers');
goog.require('cljs.core.async.impl.buffers');
goog.require('cljs.core.async.impl.timers');
cljs.core.async.fn_handler = (function fn_handler(f){if(typeof cljs.core.async.t11862 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t11862 = (function (f,fn_handler,meta11863){
this.f = f;
this.fn_handler = fn_handler;
this.meta11863 = meta11863;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t11862.cljs$lang$type = true;
cljs.core.async.t11862.cljs$lang$ctorStr = "cljs.core.async/t11862";
cljs.core.async.t11862.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"cljs.core.async/t11862");
});
cljs.core.async.t11862.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t11862.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return true;
});
cljs.core.async.t11862.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return self__.f;
});
cljs.core.async.t11862.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_11864){var self__ = this;
var _11864__$1 = this;return self__.meta11863;
});
cljs.core.async.t11862.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_11864,meta11863__$1){var self__ = this;
var _11864__$1 = this;return (new cljs.core.async.t11862(self__.f,self__.fn_handler,meta11863__$1));
});
cljs.core.async.__GT_t11862 = (function __GT_t11862(f__$1,fn_handler__$1,meta11863){return (new cljs.core.async.t11862(f__$1,fn_handler__$1,meta11863));
});
}
return (new cljs.core.async.t11862(f,fn_handler,null));
});
/**
* Returns a fixed buffer of size n. When full, puts will block/park.
*/
cljs.core.async.buffer = (function buffer(n){return cljs.core.async.impl.buffers.fixed_buffer.call(null,n);
});
/**
* Returns a buffer of size n. When full, puts will complete but
* val will be dropped (no transfer).
*/
cljs.core.async.dropping_buffer = (function dropping_buffer(n){return cljs.core.async.impl.buffers.dropping_buffer.call(null,n);
});
/**
* Returns a buffer of size n. When full, puts will complete, and be
* buffered, but oldest elements in buffer will be dropped (not
* transferred).
*/
cljs.core.async.sliding_buffer = (function sliding_buffer(n){return cljs.core.async.impl.buffers.sliding_buffer.call(null,n);
});
/**
* Returns true if a channel created with buff will never block. That is to say,
* puts into this buffer will never cause the buffer to be full.
*/
cljs.core.async.unblocking_buffer_QMARK_ = (function unblocking_buffer_QMARK_(buff){var G__11866 = buff;if(G__11866)
{var bit__4248__auto__ = null;if(cljs.core.truth_((function (){var or__3585__auto__ = bit__4248__auto__;if(cljs.core.truth_(or__3585__auto__))
{return or__3585__auto__;
} else
{return G__11866.cljs$core$async$impl$protocols$UnblockingBuffer$;
}
})()))
{return true;
} else
{if((!G__11866.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,cljs.core.async.impl.protocols.UnblockingBuffer,G__11866);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,cljs.core.async.impl.protocols.UnblockingBuffer,G__11866);
}
});
/**
* Creates a channel with an optional buffer, an optional transducer (like (map f),
* (filter p) etc or a composition thereof), and an optional exception handler.
* If buf-or-n is a number, will create and use a fixed buffer of that size. If a
* transducer is supplied a buffer must be specified. ex-handler must be a
* fn of one argument - if an exception occurs during transformation it will be called
* with the thrown value as an argument, and any non-nil return value will be placed
* in the channel.
*/
cljs.core.async.chan = (function() {
var chan = null;
var chan__0 = (function (){return chan.call(null,null);
});
var chan__1 = (function (buf_or_n){return chan.call(null,buf_or_n,null,null);
});
var chan__2 = (function (buf_or_n,xform){return chan.call(null,buf_or_n,xform,null);
});
var chan__3 = (function (buf_or_n,xform,ex_handler){var buf_or_n__$1 = ((cljs.core._EQ_.call(null,buf_or_n,(0)))?null:buf_or_n);if(cljs.core.truth_(xform))
{if(cljs.core.truth_(buf_or_n__$1))
{} else
{throw (new Error(("Assert failed: buffer must be supplied when transducer is\n"+cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.pr_str.call(null,new cljs.core.Symbol(null,"buf-or-n","buf-or-n",-1646815050,null))))));
}
} else
{}
return cljs.core.async.impl.channels.chan.call(null,((typeof buf_or_n__$1 === 'number')?cljs.core.async.buffer.call(null,buf_or_n__$1):buf_or_n__$1),xform,ex_handler);
});
chan = function(buf_or_n,xform,ex_handler){
switch(arguments.length){
case 0:
return chan__0.call(this);
case 1:
return chan__1.call(this,buf_or_n);
case 2:
return chan__2.call(this,buf_or_n,xform);
case 3:
return chan__3.call(this,buf_or_n,xform,ex_handler);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
chan.cljs$core$IFn$_invoke$arity$0 = chan__0;
chan.cljs$core$IFn$_invoke$arity$1 = chan__1;
chan.cljs$core$IFn$_invoke$arity$2 = chan__2;
chan.cljs$core$IFn$_invoke$arity$3 = chan__3;
return chan;
})()
;
/**
* Returns a channel that will close after msecs
*/
cljs.core.async.timeout = (function timeout(msecs){return cljs.core.async.impl.timers.timeout.call(null,msecs);
});
/**
* takes a val from port. Must be called inside a (go ...) block. Will
* return nil if closed. Will park if nothing is available.
* Returns true unless port is already closed
*/
cljs.core.async._LT__BANG_ = (function _LT__BANG_(port){throw (new Error("<! used not in (go ...) block"));
});
/**
* Asynchronously takes a val from port, passing to fn1. Will pass nil
* if closed. If on-caller? (default true) is true, and value is
* immediately available, will call fn1 on calling thread.
* Returns nil.
*/
cljs.core.async.take_BANG_ = (function() {
var take_BANG_ = null;
var take_BANG___2 = (function (port,fn1){return take_BANG_.call(null,port,fn1,true);
});
var take_BANG___3 = (function (port,fn1,on_caller_QMARK_){var ret = cljs.core.async.impl.protocols.take_BANG_.call(null,port,cljs.core.async.fn_handler.call(null,fn1));if(cljs.core.truth_(ret))
{var val_11867 = cljs.core.deref.call(null,ret);if(cljs.core.truth_(on_caller_QMARK_))
{fn1.call(null,val_11867);
} else
{cljs.core.async.impl.dispatch.run.call(null,((function (val_11867,ret){
return (function (){return fn1.call(null,val_11867);
});})(val_11867,ret))
);
}
} else
{}
return null;
});
take_BANG_ = function(port,fn1,on_caller_QMARK_){
switch(arguments.length){
case 2:
return take_BANG___2.call(this,port,fn1);
case 3:
return take_BANG___3.call(this,port,fn1,on_caller_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
take_BANG_.cljs$core$IFn$_invoke$arity$2 = take_BANG___2;
take_BANG_.cljs$core$IFn$_invoke$arity$3 = take_BANG___3;
return take_BANG_;
})()
;
cljs.core.async.nop = (function nop(_){return null;
});
cljs.core.async.fhnop = cljs.core.async.fn_handler.call(null,cljs.core.async.nop);
/**
* puts a val into port. nil values are not allowed. Must be called
* inside a (go ...) block. Will park if no buffer space is available.
* Returns true unless port is already closed.
*/
cljs.core.async._GT__BANG_ = (function _GT__BANG_(port,val){throw (new Error(">! used not in (go ...) block"));
});
/**
* Asynchronously puts a val into port, calling fn0 (if supplied) when
* complete. nil values are not allowed. Will throw if closed. If
* on-caller? (default true) is true, and the put is immediately
* accepted, will call fn0 on calling thread.  Returns nil.
*/
cljs.core.async.put_BANG_ = (function() {
var put_BANG_ = null;
var put_BANG___2 = (function (port,val){var temp__4124__auto__ = cljs.core.async.impl.protocols.put_BANG_.call(null,port,val,cljs.core.async.fhnop);if(cljs.core.truth_(temp__4124__auto__))
{var ret = temp__4124__auto__;return cljs.core.deref.call(null,ret);
} else
{return true;
}
});
var put_BANG___3 = (function (port,val,fn1){return put_BANG_.call(null,port,val,fn1,true);
});
var put_BANG___4 = (function (port,val,fn1,on_caller_QMARK_){var temp__4124__auto__ = cljs.core.async.impl.protocols.put_BANG_.call(null,port,val,cljs.core.async.fn_handler.call(null,fn1));if(cljs.core.truth_(temp__4124__auto__))
{var retb = temp__4124__auto__;var ret = cljs.core.deref.call(null,retb);if(cljs.core.truth_(on_caller_QMARK_))
{fn1.call(null,ret);
} else
{cljs.core.async.impl.dispatch.run.call(null,((function (ret,retb,temp__4124__auto__){
return (function (){return fn1.call(null,ret);
});})(ret,retb,temp__4124__auto__))
);
}
return ret;
} else
{return true;
}
});
put_BANG_ = function(port,val,fn1,on_caller_QMARK_){
switch(arguments.length){
case 2:
return put_BANG___2.call(this,port,val);
case 3:
return put_BANG___3.call(this,port,val,fn1);
case 4:
return put_BANG___4.call(this,port,val,fn1,on_caller_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
put_BANG_.cljs$core$IFn$_invoke$arity$2 = put_BANG___2;
put_BANG_.cljs$core$IFn$_invoke$arity$3 = put_BANG___3;
put_BANG_.cljs$core$IFn$_invoke$arity$4 = put_BANG___4;
return put_BANG_;
})()
;
cljs.core.async.close_BANG_ = (function close_BANG_(port){return cljs.core.async.impl.protocols.close_BANG_.call(null,port);
});
cljs.core.async.random_array = (function random_array(n){var a = (new Array(n));var n__4454__auto___11868 = n;var x_11869 = (0);while(true){
if((x_11869 < n__4454__auto___11868))
{(a[x_11869] = (0));
{
var G__11870 = (x_11869 + (1));
x_11869 = G__11870;
continue;
}
} else
{}
break;
}
var i = (1);while(true){
if(cljs.core._EQ_.call(null,i,n))
{return a;
} else
{var j = cljs.core.rand_int.call(null,i);(a[i] = (a[j]));
(a[j] = i);
{
var G__11871 = (i + (1));
i = G__11871;
continue;
}
}
break;
}
});
cljs.core.async.alt_flag = (function alt_flag(){var flag = cljs.core.atom.call(null,true);if(typeof cljs.core.async.t11875 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t11875 = (function (flag,alt_flag,meta11876){
this.flag = flag;
this.alt_flag = alt_flag;
this.meta11876 = meta11876;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t11875.cljs$lang$type = true;
cljs.core.async.t11875.cljs$lang$ctorStr = "cljs.core.async/t11875";
cljs.core.async.t11875.cljs$lang$ctorPrWriter = ((function (flag){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"cljs.core.async/t11875");
});})(flag))
;
cljs.core.async.t11875.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t11875.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = ((function (flag){
return (function (_){var self__ = this;
var ___$1 = this;return cljs.core.deref.call(null,self__.flag);
});})(flag))
;
cljs.core.async.t11875.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = ((function (flag){
return (function (_){var self__ = this;
var ___$1 = this;cljs.core.reset_BANG_.call(null,self__.flag,null);
return true;
});})(flag))
;
cljs.core.async.t11875.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (flag){
return (function (_11877){var self__ = this;
var _11877__$1 = this;return self__.meta11876;
});})(flag))
;
cljs.core.async.t11875.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (flag){
return (function (_11877,meta11876__$1){var self__ = this;
var _11877__$1 = this;return (new cljs.core.async.t11875(self__.flag,self__.alt_flag,meta11876__$1));
});})(flag))
;
cljs.core.async.__GT_t11875 = ((function (flag){
return (function __GT_t11875(flag__$1,alt_flag__$1,meta11876){return (new cljs.core.async.t11875(flag__$1,alt_flag__$1,meta11876));
});})(flag))
;
}
return (new cljs.core.async.t11875(flag,alt_flag,null));
});
cljs.core.async.alt_handler = (function alt_handler(flag,cb){if(typeof cljs.core.async.t11881 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t11881 = (function (cb,flag,alt_handler,meta11882){
this.cb = cb;
this.flag = flag;
this.alt_handler = alt_handler;
this.meta11882 = meta11882;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t11881.cljs$lang$type = true;
cljs.core.async.t11881.cljs$lang$ctorStr = "cljs.core.async/t11881";
cljs.core.async.t11881.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"cljs.core.async/t11881");
});
cljs.core.async.t11881.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t11881.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.active_QMARK_.call(null,self__.flag);
});
cljs.core.async.t11881.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){var self__ = this;
var ___$1 = this;cljs.core.async.impl.protocols.commit.call(null,self__.flag);
return self__.cb;
});
cljs.core.async.t11881.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_11883){var self__ = this;
var _11883__$1 = this;return self__.meta11882;
});
cljs.core.async.t11881.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_11883,meta11882__$1){var self__ = this;
var _11883__$1 = this;return (new cljs.core.async.t11881(self__.cb,self__.flag,self__.alt_handler,meta11882__$1));
});
cljs.core.async.__GT_t11881 = (function __GT_t11881(cb__$1,flag__$1,alt_handler__$1,meta11882){return (new cljs.core.async.t11881(cb__$1,flag__$1,alt_handler__$1,meta11882));
});
}
return (new cljs.core.async.t11881(cb,flag,alt_handler,null));
});
/**
* returns derefable [val port] if immediate, nil if enqueued
*/
cljs.core.async.do_alts = (function do_alts(fret,ports,opts){var flag = cljs.core.async.alt_flag.call(null);var n = cljs.core.count.call(null,ports);var idxs = cljs.core.async.random_array.call(null,n);var priority = new cljs.core.Keyword(null,"priority","priority",1431093715).cljs$core$IFn$_invoke$arity$1(opts);var ret = (function (){var i = (0);while(true){
if((i < n))
{var idx = (cljs.core.truth_(priority)?i:(idxs[i]));var port = cljs.core.nth.call(null,ports,idx);var wport = ((cljs.core.vector_QMARK_.call(null,port))?port.call(null,(0)):null);var vbox = (cljs.core.truth_(wport)?(function (){var val = port.call(null,(1));return cljs.core.async.impl.protocols.put_BANG_.call(null,wport,val,cljs.core.async.alt_handler.call(null,flag,((function (i,val,idx,port,wport,flag,n,idxs,priority){
return (function (p1__11884_SHARP_){return fret.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__11884_SHARP_,wport], null));
});})(i,val,idx,port,wport,flag,n,idxs,priority))
));
})():cljs.core.async.impl.protocols.take_BANG_.call(null,port,cljs.core.async.alt_handler.call(null,flag,((function (i,idx,port,wport,flag,n,idxs,priority){
return (function (p1__11885_SHARP_){return fret.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__11885_SHARP_,port], null));
});})(i,idx,port,wport,flag,n,idxs,priority))
)));if(cljs.core.truth_(vbox))
{return cljs.core.async.impl.channels.box.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.deref.call(null,vbox),(function (){var or__3585__auto__ = wport;if(cljs.core.truth_(or__3585__auto__))
{return or__3585__auto__;
} else
{return port;
}
})()], null));
} else
{{
var G__11886 = (i + (1));
i = G__11886;
continue;
}
}
} else
{return null;
}
break;
}
})();var or__3585__auto__ = ret;if(cljs.core.truth_(or__3585__auto__))
{return or__3585__auto__;
} else
{if(cljs.core.contains_QMARK_.call(null,opts,new cljs.core.Keyword(null,"default","default",-1987822328)))
{var temp__4126__auto__ = (function (){var and__3573__auto__ = cljs.core.async.impl.protocols.active_QMARK_.call(null,flag);if(cljs.core.truth_(and__3573__auto__))
{return cljs.core.async.impl.protocols.commit.call(null,flag);
} else
{return and__3573__auto__;
}
})();if(cljs.core.truth_(temp__4126__auto__))
{var got = temp__4126__auto__;return cljs.core.async.impl.channels.box.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"default","default",-1987822328).cljs$core$IFn$_invoke$arity$1(opts),new cljs.core.Keyword(null,"default","default",-1987822328)], null));
} else
{return null;
}
} else
{return null;
}
}
});
/**
* Completes at most one of several channel operations. Must be called
* inside a (go ...) block. ports is a vector of channel endpoints,
* which can be either a channel to take from or a vector of
* [channel-to-put-to val-to-put], in any combination. Takes will be
* made as if by <!, and puts will be made as if by >!. Unless
* the :priority option is true, if more than one port operation is
* ready a non-deterministic choice will be made. If no operation is
* ready and a :default value is supplied, [default-val :default] will
* be returned, otherwise alts! will park until the first operation to
* become ready completes. Returns [val port] of the completed
* operation, where val is the value taken for takes, and a
* boolean (true unless already closed, as per put!) for puts.
* 
* opts are passed as :key val ... Supported options:
* 
* :default val - the value to use if none of the operations are immediately ready
* :priority true - (default nil) when true, the operations will be tried in order.
* 
* Note: there is no guarantee that the port exps or val exprs will be
* used, nor in what order should they be, so they should not be
* depended upon for side effects.
* @param {...*} var_args
*/
cljs.core.async.alts_BANG_ = (function() { 
var alts_BANG___delegate = function (ports,p__11887){var map__11889 = p__11887;var map__11889__$1 = ((cljs.core.seq_QMARK_.call(null,map__11889))?cljs.core.apply.call(null,cljs.core.hash_map,map__11889):map__11889);var opts = map__11889__$1;throw (new Error("alts! used not in (go ...) block"));
};
var alts_BANG_ = function (ports,var_args){
var p__11887 = null;if (arguments.length > 1) {
  p__11887 = cljs.core.array_seq(Array.prototype.slice.call(arguments, 1),0);} 
return alts_BANG___delegate.call(this,ports,p__11887);};
alts_BANG_.cljs$lang$maxFixedArity = 1;
alts_BANG_.cljs$lang$applyTo = (function (arglist__11890){
var ports = cljs.core.first(arglist__11890);
var p__11887 = cljs.core.rest(arglist__11890);
return alts_BANG___delegate(ports,p__11887);
});
alts_BANG_.cljs$core$IFn$_invoke$arity$variadic = alts_BANG___delegate;
return alts_BANG_;
})()
;
/**
* Takes elements from the from channel and supplies them to the to
* channel. By default, the to channel will be closed when the from
* channel closes, but can be determined by the close?  parameter. Will
* stop consuming the from channel if the to channel closes
*/
cljs.core.async.pipe = (function() {
var pipe = null;
var pipe__2 = (function (from,to){return pipe.call(null,from,to,true);
});
var pipe__3 = (function (from,to,close_QMARK_){var c__6419__auto___11985 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___11985){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___11985){
return (function (state_11961){var state_val_11962 = (state_11961[(1)]);if((state_val_11962 === (7)))
{var inst_11957 = (state_11961[(2)]);var state_11961__$1 = state_11961;var statearr_11963_11986 = state_11961__$1;(statearr_11963_11986[(2)] = inst_11957);
(statearr_11963_11986[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11962 === (1)))
{var state_11961__$1 = state_11961;var statearr_11964_11987 = state_11961__$1;(statearr_11964_11987[(2)] = null);
(statearr_11964_11987[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11962 === (4)))
{var inst_11940 = (state_11961[(7)]);var inst_11940__$1 = (state_11961[(2)]);var inst_11941 = (inst_11940__$1 == null);var state_11961__$1 = (function (){var statearr_11965 = state_11961;(statearr_11965[(7)] = inst_11940__$1);
return statearr_11965;
})();if(cljs.core.truth_(inst_11941))
{var statearr_11966_11988 = state_11961__$1;(statearr_11966_11988[(1)] = (5));
} else
{var statearr_11967_11989 = state_11961__$1;(statearr_11967_11989[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11962 === (13)))
{var state_11961__$1 = state_11961;var statearr_11968_11990 = state_11961__$1;(statearr_11968_11990[(2)] = null);
(statearr_11968_11990[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11962 === (6)))
{var inst_11940 = (state_11961[(7)]);var state_11961__$1 = state_11961;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_11961__$1,(11),to,inst_11940);
} else
{if((state_val_11962 === (3)))
{var inst_11959 = (state_11961[(2)]);var state_11961__$1 = state_11961;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_11961__$1,inst_11959);
} else
{if((state_val_11962 === (12)))
{var state_11961__$1 = state_11961;var statearr_11969_11991 = state_11961__$1;(statearr_11969_11991[(2)] = null);
(statearr_11969_11991[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11962 === (2)))
{var state_11961__$1 = state_11961;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_11961__$1,(4),from);
} else
{if((state_val_11962 === (11)))
{var inst_11950 = (state_11961[(2)]);var state_11961__$1 = state_11961;if(cljs.core.truth_(inst_11950))
{var statearr_11970_11992 = state_11961__$1;(statearr_11970_11992[(1)] = (12));
} else
{var statearr_11971_11993 = state_11961__$1;(statearr_11971_11993[(1)] = (13));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11962 === (9)))
{var state_11961__$1 = state_11961;var statearr_11972_11994 = state_11961__$1;(statearr_11972_11994[(2)] = null);
(statearr_11972_11994[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11962 === (5)))
{var state_11961__$1 = state_11961;if(cljs.core.truth_(close_QMARK_))
{var statearr_11973_11995 = state_11961__$1;(statearr_11973_11995[(1)] = (8));
} else
{var statearr_11974_11996 = state_11961__$1;(statearr_11974_11996[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11962 === (14)))
{var inst_11955 = (state_11961[(2)]);var state_11961__$1 = state_11961;var statearr_11975_11997 = state_11961__$1;(statearr_11975_11997[(2)] = inst_11955);
(statearr_11975_11997[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11962 === (10)))
{var inst_11947 = (state_11961[(2)]);var state_11961__$1 = state_11961;var statearr_11976_11998 = state_11961__$1;(statearr_11976_11998[(2)] = inst_11947);
(statearr_11976_11998[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11962 === (8)))
{var inst_11944 = cljs.core.async.close_BANG_.call(null,to);var state_11961__$1 = state_11961;var statearr_11977_11999 = state_11961__$1;(statearr_11977_11999[(2)] = inst_11944);
(statearr_11977_11999[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___11985))
;return ((function (switch__6404__auto__,c__6419__auto___11985){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_11981 = [null,null,null,null,null,null,null,null];(statearr_11981[(0)] = state_machine__6405__auto__);
(statearr_11981[(1)] = (1));
return statearr_11981;
});
var state_machine__6405__auto____1 = (function (state_11961){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_11961);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e11982){if((e11982 instanceof Object))
{var ex__6408__auto__ = e11982;var statearr_11983_12000 = state_11961;(statearr_11983_12000[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_11961);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e11982;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12001 = state_11961;
state_11961 = G__12001;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_11961){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_11961);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___11985))
})();var state__6421__auto__ = (function (){var statearr_11984 = f__6420__auto__.call(null);(statearr_11984[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___11985);
return statearr_11984;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___11985))
);
return to;
});
pipe = function(from,to,close_QMARK_){
switch(arguments.length){
case 2:
return pipe__2.call(this,from,to);
case 3:
return pipe__3.call(this,from,to,close_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
pipe.cljs$core$IFn$_invoke$arity$2 = pipe__2;
pipe.cljs$core$IFn$_invoke$arity$3 = pipe__3;
return pipe;
})()
;
cljs.core.async.pipeline_STAR_ = (function pipeline_STAR_(n,to,xf,from,close_QMARK_,ex_handler,type){if((n > (0)))
{} else
{throw (new Error(("Assert failed: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.pr_str.call(null,cljs.core.list(new cljs.core.Symbol(null,"pos?","pos?",-244377722,null),new cljs.core.Symbol(null,"n","n",-2092305744,null)))))));
}
var jobs = cljs.core.async.chan.call(null,n);var results = cljs.core.async.chan.call(null,n);var process = ((function (jobs,results){
return (function (p__12185){var vec__12186 = p__12185;var v = cljs.core.nth.call(null,vec__12186,(0),null);var p = cljs.core.nth.call(null,vec__12186,(1),null);var job = vec__12186;if((job == null))
{cljs.core.async.close_BANG_.call(null,results);
return null;
} else
{var res = cljs.core.async.chan.call(null,(1),xf,ex_handler);var c__6419__auto___12368 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___12368,res,vec__12186,v,p,job,jobs,results){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___12368,res,vec__12186,v,p,job,jobs,results){
return (function (state_12191){var state_val_12192 = (state_12191[(1)]);if((state_val_12192 === (2)))
{var inst_12188 = (state_12191[(2)]);var inst_12189 = cljs.core.async.close_BANG_.call(null,res);var state_12191__$1 = (function (){var statearr_12193 = state_12191;(statearr_12193[(7)] = inst_12188);
return statearr_12193;
})();return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12191__$1,inst_12189);
} else
{if((state_val_12192 === (1)))
{var state_12191__$1 = state_12191;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12191__$1,(2),res,v);
} else
{return null;
}
}
});})(c__6419__auto___12368,res,vec__12186,v,p,job,jobs,results))
;return ((function (switch__6404__auto__,c__6419__auto___12368,res,vec__12186,v,p,job,jobs,results){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_12197 = [null,null,null,null,null,null,null,null];(statearr_12197[(0)] = state_machine__6405__auto__);
(statearr_12197[(1)] = (1));
return statearr_12197;
});
var state_machine__6405__auto____1 = (function (state_12191){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_12191);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e12198){if((e12198 instanceof Object))
{var ex__6408__auto__ = e12198;var statearr_12199_12369 = state_12191;(statearr_12199_12369[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12191);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12198;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12370 = state_12191;
state_12191 = G__12370;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_12191){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_12191);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___12368,res,vec__12186,v,p,job,jobs,results))
})();var state__6421__auto__ = (function (){var statearr_12200 = f__6420__auto__.call(null);(statearr_12200[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___12368);
return statearr_12200;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___12368,res,vec__12186,v,p,job,jobs,results))
);
cljs.core.async.put_BANG_.call(null,p,res);
return true;
}
});})(jobs,results))
;var async = ((function (jobs,results,process){
return (function (p__12201){var vec__12202 = p__12201;var v = cljs.core.nth.call(null,vec__12202,(0),null);var p = cljs.core.nth.call(null,vec__12202,(1),null);var job = vec__12202;if((job == null))
{cljs.core.async.close_BANG_.call(null,results);
return null;
} else
{var res = cljs.core.async.chan.call(null,(1));xf.call(null,v,res);
cljs.core.async.put_BANG_.call(null,p,res);
return true;
}
});})(jobs,results,process))
;var n__4454__auto___12371 = n;var __12372 = (0);while(true){
if((__12372 < n__4454__auto___12371))
{var G__12203_12373 = (((type instanceof cljs.core.Keyword))?type.fqn:null);switch (G__12203_12373) {
case "async":
var c__6419__auto___12375 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (__12372,c__6419__auto___12375,G__12203_12373,n__4454__auto___12371,jobs,results,process,async){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (__12372,c__6419__auto___12375,G__12203_12373,n__4454__auto___12371,jobs,results,process,async){
return (function (state_12216){var state_val_12217 = (state_12216[(1)]);if((state_val_12217 === (7)))
{var inst_12212 = (state_12216[(2)]);var state_12216__$1 = state_12216;var statearr_12218_12376 = state_12216__$1;(statearr_12218_12376[(2)] = inst_12212);
(statearr_12218_12376[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12217 === (6)))
{var state_12216__$1 = state_12216;var statearr_12219_12377 = state_12216__$1;(statearr_12219_12377[(2)] = null);
(statearr_12219_12377[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12217 === (5)))
{var state_12216__$1 = state_12216;var statearr_12220_12378 = state_12216__$1;(statearr_12220_12378[(2)] = null);
(statearr_12220_12378[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12217 === (4)))
{var inst_12206 = (state_12216[(2)]);var inst_12207 = async.call(null,inst_12206);var state_12216__$1 = state_12216;if(cljs.core.truth_(inst_12207))
{var statearr_12221_12379 = state_12216__$1;(statearr_12221_12379[(1)] = (5));
} else
{var statearr_12222_12380 = state_12216__$1;(statearr_12222_12380[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12217 === (3)))
{var inst_12214 = (state_12216[(2)]);var state_12216__$1 = state_12216;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12216__$1,inst_12214);
} else
{if((state_val_12217 === (2)))
{var state_12216__$1 = state_12216;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12216__$1,(4),jobs);
} else
{if((state_val_12217 === (1)))
{var state_12216__$1 = state_12216;var statearr_12223_12381 = state_12216__$1;(statearr_12223_12381[(2)] = null);
(statearr_12223_12381[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
});})(__12372,c__6419__auto___12375,G__12203_12373,n__4454__auto___12371,jobs,results,process,async))
;return ((function (__12372,switch__6404__auto__,c__6419__auto___12375,G__12203_12373,n__4454__auto___12371,jobs,results,process,async){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_12227 = [null,null,null,null,null,null,null];(statearr_12227[(0)] = state_machine__6405__auto__);
(statearr_12227[(1)] = (1));
return statearr_12227;
});
var state_machine__6405__auto____1 = (function (state_12216){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_12216);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e12228){if((e12228 instanceof Object))
{var ex__6408__auto__ = e12228;var statearr_12229_12382 = state_12216;(statearr_12229_12382[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12216);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12228;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12383 = state_12216;
state_12216 = G__12383;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_12216){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_12216);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(__12372,switch__6404__auto__,c__6419__auto___12375,G__12203_12373,n__4454__auto___12371,jobs,results,process,async))
})();var state__6421__auto__ = (function (){var statearr_12230 = f__6420__auto__.call(null);(statearr_12230[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___12375);
return statearr_12230;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(__12372,c__6419__auto___12375,G__12203_12373,n__4454__auto___12371,jobs,results,process,async))
);

break;
case "compute":
var c__6419__auto___12384 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (__12372,c__6419__auto___12384,G__12203_12373,n__4454__auto___12371,jobs,results,process,async){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (__12372,c__6419__auto___12384,G__12203_12373,n__4454__auto___12371,jobs,results,process,async){
return (function (state_12243){var state_val_12244 = (state_12243[(1)]);if((state_val_12244 === (7)))
{var inst_12239 = (state_12243[(2)]);var state_12243__$1 = state_12243;var statearr_12245_12385 = state_12243__$1;(statearr_12245_12385[(2)] = inst_12239);
(statearr_12245_12385[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12244 === (6)))
{var state_12243__$1 = state_12243;var statearr_12246_12386 = state_12243__$1;(statearr_12246_12386[(2)] = null);
(statearr_12246_12386[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12244 === (5)))
{var state_12243__$1 = state_12243;var statearr_12247_12387 = state_12243__$1;(statearr_12247_12387[(2)] = null);
(statearr_12247_12387[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12244 === (4)))
{var inst_12233 = (state_12243[(2)]);var inst_12234 = process.call(null,inst_12233);var state_12243__$1 = state_12243;if(cljs.core.truth_(inst_12234))
{var statearr_12248_12388 = state_12243__$1;(statearr_12248_12388[(1)] = (5));
} else
{var statearr_12249_12389 = state_12243__$1;(statearr_12249_12389[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12244 === (3)))
{var inst_12241 = (state_12243[(2)]);var state_12243__$1 = state_12243;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12243__$1,inst_12241);
} else
{if((state_val_12244 === (2)))
{var state_12243__$1 = state_12243;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12243__$1,(4),jobs);
} else
{if((state_val_12244 === (1)))
{var state_12243__$1 = state_12243;var statearr_12250_12390 = state_12243__$1;(statearr_12250_12390[(2)] = null);
(statearr_12250_12390[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
});})(__12372,c__6419__auto___12384,G__12203_12373,n__4454__auto___12371,jobs,results,process,async))
;return ((function (__12372,switch__6404__auto__,c__6419__auto___12384,G__12203_12373,n__4454__auto___12371,jobs,results,process,async){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_12254 = [null,null,null,null,null,null,null];(statearr_12254[(0)] = state_machine__6405__auto__);
(statearr_12254[(1)] = (1));
return statearr_12254;
});
var state_machine__6405__auto____1 = (function (state_12243){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_12243);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e12255){if((e12255 instanceof Object))
{var ex__6408__auto__ = e12255;var statearr_12256_12391 = state_12243;(statearr_12256_12391[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12243);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12255;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12392 = state_12243;
state_12243 = G__12392;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_12243){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_12243);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(__12372,switch__6404__auto__,c__6419__auto___12384,G__12203_12373,n__4454__auto___12371,jobs,results,process,async))
})();var state__6421__auto__ = (function (){var statearr_12257 = f__6420__auto__.call(null);(statearr_12257[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___12384);
return statearr_12257;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(__12372,c__6419__auto___12384,G__12203_12373,n__4454__auto___12371,jobs,results,process,async))
);

break;
default:
throw (new Error(("No matching clause: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(type))));

}
{
var G__12393 = (__12372 + (1));
__12372 = G__12393;
continue;
}
} else
{}
break;
}
var c__6419__auto___12394 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___12394,jobs,results,process,async){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___12394,jobs,results,process,async){
return (function (state_12279){var state_val_12280 = (state_12279[(1)]);if((state_val_12280 === (9)))
{var inst_12272 = (state_12279[(2)]);var state_12279__$1 = (function (){var statearr_12281 = state_12279;(statearr_12281[(7)] = inst_12272);
return statearr_12281;
})();var statearr_12282_12395 = state_12279__$1;(statearr_12282_12395[(2)] = null);
(statearr_12282_12395[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12280 === (8)))
{var inst_12265 = (state_12279[(8)]);var inst_12270 = (state_12279[(2)]);var state_12279__$1 = (function (){var statearr_12283 = state_12279;(statearr_12283[(9)] = inst_12270);
return statearr_12283;
})();return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12279__$1,(9),results,inst_12265);
} else
{if((state_val_12280 === (7)))
{var inst_12275 = (state_12279[(2)]);var state_12279__$1 = state_12279;var statearr_12284_12396 = state_12279__$1;(statearr_12284_12396[(2)] = inst_12275);
(statearr_12284_12396[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12280 === (6)))
{var inst_12265 = (state_12279[(8)]);var inst_12260 = (state_12279[(10)]);var inst_12265__$1 = cljs.core.async.chan.call(null,(1));var inst_12266 = cljs.core.PersistentVector.EMPTY_NODE;var inst_12267 = [inst_12260,inst_12265__$1];var inst_12268 = (new cljs.core.PersistentVector(null,2,(5),inst_12266,inst_12267,null));var state_12279__$1 = (function (){var statearr_12285 = state_12279;(statearr_12285[(8)] = inst_12265__$1);
return statearr_12285;
})();return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12279__$1,(8),jobs,inst_12268);
} else
{if((state_val_12280 === (5)))
{var inst_12263 = cljs.core.async.close_BANG_.call(null,jobs);var state_12279__$1 = state_12279;var statearr_12286_12397 = state_12279__$1;(statearr_12286_12397[(2)] = inst_12263);
(statearr_12286_12397[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12280 === (4)))
{var inst_12260 = (state_12279[(10)]);var inst_12260__$1 = (state_12279[(2)]);var inst_12261 = (inst_12260__$1 == null);var state_12279__$1 = (function (){var statearr_12287 = state_12279;(statearr_12287[(10)] = inst_12260__$1);
return statearr_12287;
})();if(cljs.core.truth_(inst_12261))
{var statearr_12288_12398 = state_12279__$1;(statearr_12288_12398[(1)] = (5));
} else
{var statearr_12289_12399 = state_12279__$1;(statearr_12289_12399[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12280 === (3)))
{var inst_12277 = (state_12279[(2)]);var state_12279__$1 = state_12279;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12279__$1,inst_12277);
} else
{if((state_val_12280 === (2)))
{var state_12279__$1 = state_12279;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12279__$1,(4),from);
} else
{if((state_val_12280 === (1)))
{var state_12279__$1 = state_12279;var statearr_12290_12400 = state_12279__$1;(statearr_12290_12400[(2)] = null);
(statearr_12290_12400[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___12394,jobs,results,process,async))
;return ((function (switch__6404__auto__,c__6419__auto___12394,jobs,results,process,async){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_12294 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_12294[(0)] = state_machine__6405__auto__);
(statearr_12294[(1)] = (1));
return statearr_12294;
});
var state_machine__6405__auto____1 = (function (state_12279){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_12279);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e12295){if((e12295 instanceof Object))
{var ex__6408__auto__ = e12295;var statearr_12296_12401 = state_12279;(statearr_12296_12401[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12279);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12295;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12402 = state_12279;
state_12279 = G__12402;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_12279){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_12279);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___12394,jobs,results,process,async))
})();var state__6421__auto__ = (function (){var statearr_12297 = f__6420__auto__.call(null);(statearr_12297[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___12394);
return statearr_12297;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___12394,jobs,results,process,async))
);
var c__6419__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto__,jobs,results,process,async){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto__,jobs,results,process,async){
return (function (state_12335){var state_val_12336 = (state_12335[(1)]);if((state_val_12336 === (7)))
{var inst_12331 = (state_12335[(2)]);var state_12335__$1 = state_12335;var statearr_12337_12403 = state_12335__$1;(statearr_12337_12403[(2)] = inst_12331);
(statearr_12337_12403[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (20)))
{var state_12335__$1 = state_12335;var statearr_12338_12404 = state_12335__$1;(statearr_12338_12404[(2)] = null);
(statearr_12338_12404[(1)] = (21));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (1)))
{var state_12335__$1 = state_12335;var statearr_12339_12405 = state_12335__$1;(statearr_12339_12405[(2)] = null);
(statearr_12339_12405[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (4)))
{var inst_12300 = (state_12335[(7)]);var inst_12300__$1 = (state_12335[(2)]);var inst_12301 = (inst_12300__$1 == null);var state_12335__$1 = (function (){var statearr_12340 = state_12335;(statearr_12340[(7)] = inst_12300__$1);
return statearr_12340;
})();if(cljs.core.truth_(inst_12301))
{var statearr_12341_12406 = state_12335__$1;(statearr_12341_12406[(1)] = (5));
} else
{var statearr_12342_12407 = state_12335__$1;(statearr_12342_12407[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (15)))
{var inst_12313 = (state_12335[(8)]);var state_12335__$1 = state_12335;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12335__$1,(18),to,inst_12313);
} else
{if((state_val_12336 === (21)))
{var inst_12326 = (state_12335[(2)]);var state_12335__$1 = state_12335;var statearr_12343_12408 = state_12335__$1;(statearr_12343_12408[(2)] = inst_12326);
(statearr_12343_12408[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (13)))
{var inst_12328 = (state_12335[(2)]);var state_12335__$1 = (function (){var statearr_12344 = state_12335;(statearr_12344[(9)] = inst_12328);
return statearr_12344;
})();var statearr_12345_12409 = state_12335__$1;(statearr_12345_12409[(2)] = null);
(statearr_12345_12409[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (6)))
{var inst_12300 = (state_12335[(7)]);var state_12335__$1 = state_12335;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12335__$1,(11),inst_12300);
} else
{if((state_val_12336 === (17)))
{var inst_12321 = (state_12335[(2)]);var state_12335__$1 = state_12335;if(cljs.core.truth_(inst_12321))
{var statearr_12346_12410 = state_12335__$1;(statearr_12346_12410[(1)] = (19));
} else
{var statearr_12347_12411 = state_12335__$1;(statearr_12347_12411[(1)] = (20));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (3)))
{var inst_12333 = (state_12335[(2)]);var state_12335__$1 = state_12335;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12335__$1,inst_12333);
} else
{if((state_val_12336 === (12)))
{var inst_12310 = (state_12335[(10)]);var state_12335__$1 = state_12335;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12335__$1,(14),inst_12310);
} else
{if((state_val_12336 === (2)))
{var state_12335__$1 = state_12335;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12335__$1,(4),results);
} else
{if((state_val_12336 === (19)))
{var state_12335__$1 = state_12335;var statearr_12348_12412 = state_12335__$1;(statearr_12348_12412[(2)] = null);
(statearr_12348_12412[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (11)))
{var inst_12310 = (state_12335[(2)]);var state_12335__$1 = (function (){var statearr_12349 = state_12335;(statearr_12349[(10)] = inst_12310);
return statearr_12349;
})();var statearr_12350_12413 = state_12335__$1;(statearr_12350_12413[(2)] = null);
(statearr_12350_12413[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (9)))
{var state_12335__$1 = state_12335;var statearr_12351_12414 = state_12335__$1;(statearr_12351_12414[(2)] = null);
(statearr_12351_12414[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (5)))
{var state_12335__$1 = state_12335;if(cljs.core.truth_(close_QMARK_))
{var statearr_12352_12415 = state_12335__$1;(statearr_12352_12415[(1)] = (8));
} else
{var statearr_12353_12416 = state_12335__$1;(statearr_12353_12416[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (14)))
{var inst_12315 = (state_12335[(11)]);var inst_12313 = (state_12335[(8)]);var inst_12313__$1 = (state_12335[(2)]);var inst_12314 = (inst_12313__$1 == null);var inst_12315__$1 = cljs.core.not.call(null,inst_12314);var state_12335__$1 = (function (){var statearr_12354 = state_12335;(statearr_12354[(11)] = inst_12315__$1);
(statearr_12354[(8)] = inst_12313__$1);
return statearr_12354;
})();if(inst_12315__$1)
{var statearr_12355_12417 = state_12335__$1;(statearr_12355_12417[(1)] = (15));
} else
{var statearr_12356_12418 = state_12335__$1;(statearr_12356_12418[(1)] = (16));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (16)))
{var inst_12315 = (state_12335[(11)]);var state_12335__$1 = state_12335;var statearr_12357_12419 = state_12335__$1;(statearr_12357_12419[(2)] = inst_12315);
(statearr_12357_12419[(1)] = (17));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (10)))
{var inst_12307 = (state_12335[(2)]);var state_12335__$1 = state_12335;var statearr_12358_12420 = state_12335__$1;(statearr_12358_12420[(2)] = inst_12307);
(statearr_12358_12420[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (18)))
{var inst_12318 = (state_12335[(2)]);var state_12335__$1 = state_12335;var statearr_12359_12421 = state_12335__$1;(statearr_12359_12421[(2)] = inst_12318);
(statearr_12359_12421[(1)] = (17));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12336 === (8)))
{var inst_12304 = cljs.core.async.close_BANG_.call(null,to);var state_12335__$1 = state_12335;var statearr_12360_12422 = state_12335__$1;(statearr_12360_12422[(2)] = inst_12304);
(statearr_12360_12422[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto__,jobs,results,process,async))
;return ((function (switch__6404__auto__,c__6419__auto__,jobs,results,process,async){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_12364 = [null,null,null,null,null,null,null,null,null,null,null,null];(statearr_12364[(0)] = state_machine__6405__auto__);
(statearr_12364[(1)] = (1));
return statearr_12364;
});
var state_machine__6405__auto____1 = (function (state_12335){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_12335);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e12365){if((e12365 instanceof Object))
{var ex__6408__auto__ = e12365;var statearr_12366_12423 = state_12335;(statearr_12366_12423[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12335);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12365;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12424 = state_12335;
state_12335 = G__12424;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_12335){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_12335);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto__,jobs,results,process,async))
})();var state__6421__auto__ = (function (){var statearr_12367 = f__6420__auto__.call(null);(statearr_12367[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto__);
return statearr_12367;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto__,jobs,results,process,async))
);
return c__6419__auto__;
});
/**
* Takes elements from the from channel and supplies them to the to
* channel, subject to the async function af, with parallelism n. af
* must be a function of two arguments, the first an input value and
* the second a channel on which to place the result(s). af must close!
* the channel before returning.  The presumption is that af will
* return immediately, having launched some asynchronous operation
* whose completion/callback will manipulate the result channel. Outputs
* will be returned in order relative to  the inputs. By default, the to
* channel will be closed when the from channel closes, but can be
* determined by the close?  parameter. Will stop consuming the from
* channel if the to channel closes.
*/
cljs.core.async.pipeline_async = (function() {
var pipeline_async = null;
var pipeline_async__4 = (function (n,to,af,from){return pipeline_async.call(null,n,to,af,from,true);
});
var pipeline_async__5 = (function (n,to,af,from,close_QMARK_){return cljs.core.async.pipeline_STAR_.call(null,n,to,af,from,close_QMARK_,null,new cljs.core.Keyword(null,"async","async",1050769601));
});
pipeline_async = function(n,to,af,from,close_QMARK_){
switch(arguments.length){
case 4:
return pipeline_async__4.call(this,n,to,af,from);
case 5:
return pipeline_async__5.call(this,n,to,af,from,close_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
pipeline_async.cljs$core$IFn$_invoke$arity$4 = pipeline_async__4;
pipeline_async.cljs$core$IFn$_invoke$arity$5 = pipeline_async__5;
return pipeline_async;
})()
;
/**
* Takes elements from the from channel and supplies them to the to
* channel, subject to the transducer xf, with parallelism n. Because
* it is parallel, the transducer will be applied independently to each
* element, not across elements, and may produce zero or more outputs
* per input.  Outputs will be returned in order relative to the
* inputs. By default, the to channel will be closed when the from
* channel closes, but can be determined by the close?  parameter. Will
* stop consuming the from channel if the to channel closes.
* 
* Note this is supplied for API compatibility with the Clojure version.
* Values of N > 1 will not result in actual concurrency in a
* single-threaded runtime.
*/
cljs.core.async.pipeline = (function() {
var pipeline = null;
var pipeline__4 = (function (n,to,xf,from){return pipeline.call(null,n,to,xf,from,true);
});
var pipeline__5 = (function (n,to,xf,from,close_QMARK_){return pipeline.call(null,n,to,xf,from,close_QMARK_,null);
});
var pipeline__6 = (function (n,to,xf,from,close_QMARK_,ex_handler){return cljs.core.async.pipeline_STAR_.call(null,n,to,xf,from,close_QMARK_,ex_handler,new cljs.core.Keyword(null,"compute","compute",1555393130));
});
pipeline = function(n,to,xf,from,close_QMARK_,ex_handler){
switch(arguments.length){
case 4:
return pipeline__4.call(this,n,to,xf,from);
case 5:
return pipeline__5.call(this,n,to,xf,from,close_QMARK_);
case 6:
return pipeline__6.call(this,n,to,xf,from,close_QMARK_,ex_handler);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
pipeline.cljs$core$IFn$_invoke$arity$4 = pipeline__4;
pipeline.cljs$core$IFn$_invoke$arity$5 = pipeline__5;
pipeline.cljs$core$IFn$_invoke$arity$6 = pipeline__6;
return pipeline;
})()
;
/**
* Takes a predicate and a source channel and returns a vector of two
* channels, the first of which will contain the values for which the
* predicate returned true, the second those for which it returned
* false.
* 
* The out channels will be unbuffered by default, or two buf-or-ns can
* be supplied. The channels will close after the source channel has
* closed.
*/
cljs.core.async.split = (function() {
var split = null;
var split__2 = (function (p,ch){return split.call(null,p,ch,null,null);
});
var split__4 = (function (p,ch,t_buf_or_n,f_buf_or_n){var tc = cljs.core.async.chan.call(null,t_buf_or_n);var fc = cljs.core.async.chan.call(null,f_buf_or_n);var c__6419__auto___12525 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___12525,tc,fc){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___12525,tc,fc){
return (function (state_12500){var state_val_12501 = (state_12500[(1)]);if((state_val_12501 === (7)))
{var inst_12496 = (state_12500[(2)]);var state_12500__$1 = state_12500;var statearr_12502_12526 = state_12500__$1;(statearr_12502_12526[(2)] = inst_12496);
(statearr_12502_12526[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12501 === (1)))
{var state_12500__$1 = state_12500;var statearr_12503_12527 = state_12500__$1;(statearr_12503_12527[(2)] = null);
(statearr_12503_12527[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12501 === (4)))
{var inst_12477 = (state_12500[(7)]);var inst_12477__$1 = (state_12500[(2)]);var inst_12478 = (inst_12477__$1 == null);var state_12500__$1 = (function (){var statearr_12504 = state_12500;(statearr_12504[(7)] = inst_12477__$1);
return statearr_12504;
})();if(cljs.core.truth_(inst_12478))
{var statearr_12505_12528 = state_12500__$1;(statearr_12505_12528[(1)] = (5));
} else
{var statearr_12506_12529 = state_12500__$1;(statearr_12506_12529[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12501 === (13)))
{var state_12500__$1 = state_12500;var statearr_12507_12530 = state_12500__$1;(statearr_12507_12530[(2)] = null);
(statearr_12507_12530[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12501 === (6)))
{var inst_12477 = (state_12500[(7)]);var inst_12483 = p.call(null,inst_12477);var state_12500__$1 = state_12500;if(cljs.core.truth_(inst_12483))
{var statearr_12508_12531 = state_12500__$1;(statearr_12508_12531[(1)] = (9));
} else
{var statearr_12509_12532 = state_12500__$1;(statearr_12509_12532[(1)] = (10));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12501 === (3)))
{var inst_12498 = (state_12500[(2)]);var state_12500__$1 = state_12500;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12500__$1,inst_12498);
} else
{if((state_val_12501 === (12)))
{var state_12500__$1 = state_12500;var statearr_12510_12533 = state_12500__$1;(statearr_12510_12533[(2)] = null);
(statearr_12510_12533[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12501 === (2)))
{var state_12500__$1 = state_12500;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12500__$1,(4),ch);
} else
{if((state_val_12501 === (11)))
{var inst_12477 = (state_12500[(7)]);var inst_12487 = (state_12500[(2)]);var state_12500__$1 = state_12500;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12500__$1,(8),inst_12487,inst_12477);
} else
{if((state_val_12501 === (9)))
{var state_12500__$1 = state_12500;var statearr_12511_12534 = state_12500__$1;(statearr_12511_12534[(2)] = tc);
(statearr_12511_12534[(1)] = (11));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12501 === (5)))
{var inst_12480 = cljs.core.async.close_BANG_.call(null,tc);var inst_12481 = cljs.core.async.close_BANG_.call(null,fc);var state_12500__$1 = (function (){var statearr_12512 = state_12500;(statearr_12512[(8)] = inst_12480);
return statearr_12512;
})();var statearr_12513_12535 = state_12500__$1;(statearr_12513_12535[(2)] = inst_12481);
(statearr_12513_12535[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12501 === (14)))
{var inst_12494 = (state_12500[(2)]);var state_12500__$1 = state_12500;var statearr_12514_12536 = state_12500__$1;(statearr_12514_12536[(2)] = inst_12494);
(statearr_12514_12536[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12501 === (10)))
{var state_12500__$1 = state_12500;var statearr_12515_12537 = state_12500__$1;(statearr_12515_12537[(2)] = fc);
(statearr_12515_12537[(1)] = (11));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12501 === (8)))
{var inst_12489 = (state_12500[(2)]);var state_12500__$1 = state_12500;if(cljs.core.truth_(inst_12489))
{var statearr_12516_12538 = state_12500__$1;(statearr_12516_12538[(1)] = (12));
} else
{var statearr_12517_12539 = state_12500__$1;(statearr_12517_12539[(1)] = (13));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___12525,tc,fc))
;return ((function (switch__6404__auto__,c__6419__auto___12525,tc,fc){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_12521 = [null,null,null,null,null,null,null,null,null];(statearr_12521[(0)] = state_machine__6405__auto__);
(statearr_12521[(1)] = (1));
return statearr_12521;
});
var state_machine__6405__auto____1 = (function (state_12500){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_12500);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e12522){if((e12522 instanceof Object))
{var ex__6408__auto__ = e12522;var statearr_12523_12540 = state_12500;(statearr_12523_12540[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12500);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12522;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12541 = state_12500;
state_12500 = G__12541;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_12500){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_12500);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___12525,tc,fc))
})();var state__6421__auto__ = (function (){var statearr_12524 = f__6420__auto__.call(null);(statearr_12524[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___12525);
return statearr_12524;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___12525,tc,fc))
);
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [tc,fc], null);
});
split = function(p,ch,t_buf_or_n,f_buf_or_n){
switch(arguments.length){
case 2:
return split__2.call(this,p,ch);
case 4:
return split__4.call(this,p,ch,t_buf_or_n,f_buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
split.cljs$core$IFn$_invoke$arity$2 = split__2;
split.cljs$core$IFn$_invoke$arity$4 = split__4;
return split;
})()
;
/**
* f should be a function of 2 arguments. Returns a channel containing
* the single result of applying f to init and the first item from the
* channel, then applying f to that result and the 2nd item, etc. If
* the channel closes without yielding items, returns init and f is not
* called. ch must close before reduce produces a result.
*/
cljs.core.async.reduce = (function reduce(f,init,ch){var c__6419__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto__){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto__){
return (function (state_12588){var state_val_12589 = (state_12588[(1)]);if((state_val_12589 === (7)))
{var inst_12584 = (state_12588[(2)]);var state_12588__$1 = state_12588;var statearr_12590_12606 = state_12588__$1;(statearr_12590_12606[(2)] = inst_12584);
(statearr_12590_12606[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12589 === (6)))
{var inst_12574 = (state_12588[(7)]);var inst_12577 = (state_12588[(8)]);var inst_12581 = f.call(null,inst_12574,inst_12577);var inst_12574__$1 = inst_12581;var state_12588__$1 = (function (){var statearr_12591 = state_12588;(statearr_12591[(7)] = inst_12574__$1);
return statearr_12591;
})();var statearr_12592_12607 = state_12588__$1;(statearr_12592_12607[(2)] = null);
(statearr_12592_12607[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12589 === (5)))
{var inst_12574 = (state_12588[(7)]);var state_12588__$1 = state_12588;var statearr_12593_12608 = state_12588__$1;(statearr_12593_12608[(2)] = inst_12574);
(statearr_12593_12608[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12589 === (4)))
{var inst_12577 = (state_12588[(8)]);var inst_12577__$1 = (state_12588[(2)]);var inst_12578 = (inst_12577__$1 == null);var state_12588__$1 = (function (){var statearr_12594 = state_12588;(statearr_12594[(8)] = inst_12577__$1);
return statearr_12594;
})();if(cljs.core.truth_(inst_12578))
{var statearr_12595_12609 = state_12588__$1;(statearr_12595_12609[(1)] = (5));
} else
{var statearr_12596_12610 = state_12588__$1;(statearr_12596_12610[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12589 === (3)))
{var inst_12586 = (state_12588[(2)]);var state_12588__$1 = state_12588;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12588__$1,inst_12586);
} else
{if((state_val_12589 === (2)))
{var state_12588__$1 = state_12588;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12588__$1,(4),ch);
} else
{if((state_val_12589 === (1)))
{var inst_12574 = init;var state_12588__$1 = (function (){var statearr_12597 = state_12588;(statearr_12597[(7)] = inst_12574);
return statearr_12597;
})();var statearr_12598_12611 = state_12588__$1;(statearr_12598_12611[(2)] = null);
(statearr_12598_12611[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
});})(c__6419__auto__))
;return ((function (switch__6404__auto__,c__6419__auto__){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_12602 = [null,null,null,null,null,null,null,null,null];(statearr_12602[(0)] = state_machine__6405__auto__);
(statearr_12602[(1)] = (1));
return statearr_12602;
});
var state_machine__6405__auto____1 = (function (state_12588){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_12588);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e12603){if((e12603 instanceof Object))
{var ex__6408__auto__ = e12603;var statearr_12604_12612 = state_12588;(statearr_12604_12612[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12588);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12603;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12613 = state_12588;
state_12588 = G__12613;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_12588){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_12588);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto__))
})();var state__6421__auto__ = (function (){var statearr_12605 = f__6420__auto__.call(null);(statearr_12605[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto__);
return statearr_12605;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto__))
);
return c__6419__auto__;
});
/**
* Puts the contents of coll into the supplied channel.
* 
* By default the channel will be closed after the items are copied,
* but can be determined by the close? parameter.
* 
* Returns a channel which will close after the items are copied.
*/
cljs.core.async.onto_chan = (function() {
var onto_chan = null;
var onto_chan__2 = (function (ch,coll){return onto_chan.call(null,ch,coll,true);
});
var onto_chan__3 = (function (ch,coll,close_QMARK_){var c__6419__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto__){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto__){
return (function (state_12687){var state_val_12688 = (state_12687[(1)]);if((state_val_12688 === (7)))
{var inst_12669 = (state_12687[(2)]);var state_12687__$1 = state_12687;var statearr_12689_12712 = state_12687__$1;(statearr_12689_12712[(2)] = inst_12669);
(statearr_12689_12712[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12688 === (1)))
{var inst_12663 = cljs.core.seq.call(null,coll);var inst_12664 = inst_12663;var state_12687__$1 = (function (){var statearr_12690 = state_12687;(statearr_12690[(7)] = inst_12664);
return statearr_12690;
})();var statearr_12691_12713 = state_12687__$1;(statearr_12691_12713[(2)] = null);
(statearr_12691_12713[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12688 === (4)))
{var inst_12664 = (state_12687[(7)]);var inst_12667 = cljs.core.first.call(null,inst_12664);var state_12687__$1 = state_12687;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12687__$1,(7),ch,inst_12667);
} else
{if((state_val_12688 === (13)))
{var inst_12681 = (state_12687[(2)]);var state_12687__$1 = state_12687;var statearr_12692_12714 = state_12687__$1;(statearr_12692_12714[(2)] = inst_12681);
(statearr_12692_12714[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12688 === (6)))
{var inst_12672 = (state_12687[(2)]);var state_12687__$1 = state_12687;if(cljs.core.truth_(inst_12672))
{var statearr_12693_12715 = state_12687__$1;(statearr_12693_12715[(1)] = (8));
} else
{var statearr_12694_12716 = state_12687__$1;(statearr_12694_12716[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12688 === (3)))
{var inst_12685 = (state_12687[(2)]);var state_12687__$1 = state_12687;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12687__$1,inst_12685);
} else
{if((state_val_12688 === (12)))
{var state_12687__$1 = state_12687;var statearr_12695_12717 = state_12687__$1;(statearr_12695_12717[(2)] = null);
(statearr_12695_12717[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12688 === (2)))
{var inst_12664 = (state_12687[(7)]);var state_12687__$1 = state_12687;if(cljs.core.truth_(inst_12664))
{var statearr_12696_12718 = state_12687__$1;(statearr_12696_12718[(1)] = (4));
} else
{var statearr_12697_12719 = state_12687__$1;(statearr_12697_12719[(1)] = (5));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12688 === (11)))
{var inst_12678 = cljs.core.async.close_BANG_.call(null,ch);var state_12687__$1 = state_12687;var statearr_12698_12720 = state_12687__$1;(statearr_12698_12720[(2)] = inst_12678);
(statearr_12698_12720[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12688 === (9)))
{var state_12687__$1 = state_12687;if(cljs.core.truth_(close_QMARK_))
{var statearr_12699_12721 = state_12687__$1;(statearr_12699_12721[(1)] = (11));
} else
{var statearr_12700_12722 = state_12687__$1;(statearr_12700_12722[(1)] = (12));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12688 === (5)))
{var inst_12664 = (state_12687[(7)]);var state_12687__$1 = state_12687;var statearr_12701_12723 = state_12687__$1;(statearr_12701_12723[(2)] = inst_12664);
(statearr_12701_12723[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12688 === (10)))
{var inst_12683 = (state_12687[(2)]);var state_12687__$1 = state_12687;var statearr_12702_12724 = state_12687__$1;(statearr_12702_12724[(2)] = inst_12683);
(statearr_12702_12724[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12688 === (8)))
{var inst_12664 = (state_12687[(7)]);var inst_12674 = cljs.core.next.call(null,inst_12664);var inst_12664__$1 = inst_12674;var state_12687__$1 = (function (){var statearr_12703 = state_12687;(statearr_12703[(7)] = inst_12664__$1);
return statearr_12703;
})();var statearr_12704_12725 = state_12687__$1;(statearr_12704_12725[(2)] = null);
(statearr_12704_12725[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto__))
;return ((function (switch__6404__auto__,c__6419__auto__){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_12708 = [null,null,null,null,null,null,null,null];(statearr_12708[(0)] = state_machine__6405__auto__);
(statearr_12708[(1)] = (1));
return statearr_12708;
});
var state_machine__6405__auto____1 = (function (state_12687){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_12687);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e12709){if((e12709 instanceof Object))
{var ex__6408__auto__ = e12709;var statearr_12710_12726 = state_12687;(statearr_12710_12726[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12687);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12709;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12727 = state_12687;
state_12687 = G__12727;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_12687){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_12687);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto__))
})();var state__6421__auto__ = (function (){var statearr_12711 = f__6420__auto__.call(null);(statearr_12711[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto__);
return statearr_12711;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto__))
);
return c__6419__auto__;
});
onto_chan = function(ch,coll,close_QMARK_){
switch(arguments.length){
case 2:
return onto_chan__2.call(this,ch,coll);
case 3:
return onto_chan__3.call(this,ch,coll,close_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
onto_chan.cljs$core$IFn$_invoke$arity$2 = onto_chan__2;
onto_chan.cljs$core$IFn$_invoke$arity$3 = onto_chan__3;
return onto_chan;
})()
;
/**
* Creates and returns a channel which contains the contents of coll,
* closing when exhausted.
*/
cljs.core.async.to_chan = (function to_chan(coll){var ch = cljs.core.async.chan.call(null,cljs.core.bounded_count.call(null,(100),coll));cljs.core.async.onto_chan.call(null,ch,coll);
return ch;
});
cljs.core.async.Mux = (function (){var obj12729 = {};return obj12729;
})();
cljs.core.async.muxch_STAR_ = (function muxch_STAR_(_){if((function (){var and__3573__auto__ = _;if(and__3573__auto__)
{return _.cljs$core$async$Mux$muxch_STAR_$arity$1;
} else
{return and__3573__auto__;
}
})())
{return _.cljs$core$async$Mux$muxch_STAR_$arity$1(_);
} else
{var x__4221__auto__ = (((_ == null))?null:_);return (function (){var or__3585__auto__ = (cljs.core.async.muxch_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.muxch_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mux.muxch*",_);
}
}
})().call(null,_);
}
});
cljs.core.async.Mult = (function (){var obj12731 = {};return obj12731;
})();
cljs.core.async.tap_STAR_ = (function tap_STAR_(m,ch,close_QMARK_){if((function (){var and__3573__auto__ = m;if(and__3573__auto__)
{return m.cljs$core$async$Mult$tap_STAR_$arity$3;
} else
{return and__3573__auto__;
}
})())
{return m.cljs$core$async$Mult$tap_STAR_$arity$3(m,ch,close_QMARK_);
} else
{var x__4221__auto__ = (((m == null))?null:m);return (function (){var or__3585__auto__ = (cljs.core.async.tap_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.tap_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mult.tap*",m);
}
}
})().call(null,m,ch,close_QMARK_);
}
});
cljs.core.async.untap_STAR_ = (function untap_STAR_(m,ch){if((function (){var and__3573__auto__ = m;if(and__3573__auto__)
{return m.cljs$core$async$Mult$untap_STAR_$arity$2;
} else
{return and__3573__auto__;
}
})())
{return m.cljs$core$async$Mult$untap_STAR_$arity$2(m,ch);
} else
{var x__4221__auto__ = (((m == null))?null:m);return (function (){var or__3585__auto__ = (cljs.core.async.untap_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.untap_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mult.untap*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.untap_all_STAR_ = (function untap_all_STAR_(m){if((function (){var and__3573__auto__ = m;if(and__3573__auto__)
{return m.cljs$core$async$Mult$untap_all_STAR_$arity$1;
} else
{return and__3573__auto__;
}
})())
{return m.cljs$core$async$Mult$untap_all_STAR_$arity$1(m);
} else
{var x__4221__auto__ = (((m == null))?null:m);return (function (){var or__3585__auto__ = (cljs.core.async.untap_all_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.untap_all_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mult.untap-all*",m);
}
}
})().call(null,m);
}
});
/**
* Creates and returns a mult(iple) of the supplied channel. Channels
* containing copies of the channel can be created with 'tap', and
* detached with 'untap'.
* 
* Each item is distributed to all taps in parallel and synchronously,
* i.e. each tap must accept before the next item is distributed. Use
* buffering/windowing to prevent slow taps from holding up the mult.
* 
* Items received when there are no taps get dropped.
* 
* If a tap puts to a closed channel, it will be removed from the mult.
*/
cljs.core.async.mult = (function mult(ch){var cs = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);var m = (function (){if(typeof cljs.core.async.t12953 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12953 = (function (cs,ch,mult,meta12954){
this.cs = cs;
this.ch = ch;
this.mult = mult;
this.meta12954 = meta12954;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12953.cljs$lang$type = true;
cljs.core.async.t12953.cljs$lang$ctorStr = "cljs.core.async/t12953";
cljs.core.async.t12953.cljs$lang$ctorPrWriter = ((function (cs){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"cljs.core.async/t12953");
});})(cs))
;
cljs.core.async.t12953.prototype.cljs$core$async$Mult$ = true;
cljs.core.async.t12953.prototype.cljs$core$async$Mult$tap_STAR_$arity$3 = ((function (cs){
return (function (_,ch__$2,close_QMARK_){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.assoc,ch__$2,close_QMARK_);
return null;
});})(cs))
;
cljs.core.async.t12953.prototype.cljs$core$async$Mult$untap_STAR_$arity$2 = ((function (cs){
return (function (_,ch__$2){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.dissoc,ch__$2);
return null;
});})(cs))
;
cljs.core.async.t12953.prototype.cljs$core$async$Mult$untap_all_STAR_$arity$1 = ((function (cs){
return (function (_){var self__ = this;
var ___$1 = this;cljs.core.reset_BANG_.call(null,self__.cs,cljs.core.PersistentArrayMap.EMPTY);
return null;
});})(cs))
;
cljs.core.async.t12953.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t12953.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (cs){
return (function (_){var self__ = this;
var ___$1 = this;return self__.ch;
});})(cs))
;
cljs.core.async.t12953.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (cs){
return (function (_12955){var self__ = this;
var _12955__$1 = this;return self__.meta12954;
});})(cs))
;
cljs.core.async.t12953.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (cs){
return (function (_12955,meta12954__$1){var self__ = this;
var _12955__$1 = this;return (new cljs.core.async.t12953(self__.cs,self__.ch,self__.mult,meta12954__$1));
});})(cs))
;
cljs.core.async.__GT_t12953 = ((function (cs){
return (function __GT_t12953(cs__$1,ch__$1,mult__$1,meta12954){return (new cljs.core.async.t12953(cs__$1,ch__$1,mult__$1,meta12954));
});})(cs))
;
}
return (new cljs.core.async.t12953(cs,ch,mult,null));
})();var dchan = cljs.core.async.chan.call(null,(1));var dctr = cljs.core.atom.call(null,null);var done = ((function (cs,m,dchan,dctr){
return (function (_){if((cljs.core.swap_BANG_.call(null,dctr,cljs.core.dec) === (0)))
{return cljs.core.async.put_BANG_.call(null,dchan,true);
} else
{return null;
}
});})(cs,m,dchan,dctr))
;var c__6419__auto___13174 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___13174,cs,m,dchan,dctr,done){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___13174,cs,m,dchan,dctr,done){
return (function (state_13086){var state_val_13087 = (state_13086[(1)]);if((state_val_13087 === (7)))
{var inst_13082 = (state_13086[(2)]);var state_13086__$1 = state_13086;var statearr_13088_13175 = state_13086__$1;(statearr_13088_13175[(2)] = inst_13082);
(statearr_13088_13175[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (20)))
{var inst_12987 = (state_13086[(7)]);var inst_12997 = cljs.core.first.call(null,inst_12987);var inst_12998 = cljs.core.nth.call(null,inst_12997,(0),null);var inst_12999 = cljs.core.nth.call(null,inst_12997,(1),null);var state_13086__$1 = (function (){var statearr_13089 = state_13086;(statearr_13089[(8)] = inst_12998);
return statearr_13089;
})();if(cljs.core.truth_(inst_12999))
{var statearr_13090_13176 = state_13086__$1;(statearr_13090_13176[(1)] = (22));
} else
{var statearr_13091_13177 = state_13086__$1;(statearr_13091_13177[(1)] = (23));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (27)))
{var inst_13027 = (state_13086[(9)]);var inst_13029 = (state_13086[(10)]);var inst_12958 = (state_13086[(11)]);var inst_13034 = (state_13086[(12)]);var inst_13034__$1 = cljs.core._nth.call(null,inst_13027,inst_13029);var inst_13035 = cljs.core.async.put_BANG_.call(null,inst_13034__$1,inst_12958,done);var state_13086__$1 = (function (){var statearr_13092 = state_13086;(statearr_13092[(12)] = inst_13034__$1);
return statearr_13092;
})();if(cljs.core.truth_(inst_13035))
{var statearr_13093_13178 = state_13086__$1;(statearr_13093_13178[(1)] = (30));
} else
{var statearr_13094_13179 = state_13086__$1;(statearr_13094_13179[(1)] = (31));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (1)))
{var state_13086__$1 = state_13086;var statearr_13095_13180 = state_13086__$1;(statearr_13095_13180[(2)] = null);
(statearr_13095_13180[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (24)))
{var inst_12987 = (state_13086[(7)]);var inst_13004 = (state_13086[(2)]);var inst_13005 = cljs.core.next.call(null,inst_12987);var inst_12967 = inst_13005;var inst_12968 = null;var inst_12969 = (0);var inst_12970 = (0);var state_13086__$1 = (function (){var statearr_13096 = state_13086;(statearr_13096[(13)] = inst_12969);
(statearr_13096[(14)] = inst_12967);
(statearr_13096[(15)] = inst_13004);
(statearr_13096[(16)] = inst_12968);
(statearr_13096[(17)] = inst_12970);
return statearr_13096;
})();var statearr_13097_13181 = state_13086__$1;(statearr_13097_13181[(2)] = null);
(statearr_13097_13181[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (39)))
{var state_13086__$1 = state_13086;var statearr_13101_13182 = state_13086__$1;(statearr_13101_13182[(2)] = null);
(statearr_13101_13182[(1)] = (41));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (4)))
{var inst_12958 = (state_13086[(11)]);var inst_12958__$1 = (state_13086[(2)]);var inst_12959 = (inst_12958__$1 == null);var state_13086__$1 = (function (){var statearr_13102 = state_13086;(statearr_13102[(11)] = inst_12958__$1);
return statearr_13102;
})();if(cljs.core.truth_(inst_12959))
{var statearr_13103_13183 = state_13086__$1;(statearr_13103_13183[(1)] = (5));
} else
{var statearr_13104_13184 = state_13086__$1;(statearr_13104_13184[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (15)))
{var inst_12969 = (state_13086[(13)]);var inst_12967 = (state_13086[(14)]);var inst_12968 = (state_13086[(16)]);var inst_12970 = (state_13086[(17)]);var inst_12983 = (state_13086[(2)]);var inst_12984 = (inst_12970 + (1));var tmp13098 = inst_12969;var tmp13099 = inst_12967;var tmp13100 = inst_12968;var inst_12967__$1 = tmp13099;var inst_12968__$1 = tmp13100;var inst_12969__$1 = tmp13098;var inst_12970__$1 = inst_12984;var state_13086__$1 = (function (){var statearr_13105 = state_13086;(statearr_13105[(18)] = inst_12983);
(statearr_13105[(13)] = inst_12969__$1);
(statearr_13105[(14)] = inst_12967__$1);
(statearr_13105[(16)] = inst_12968__$1);
(statearr_13105[(17)] = inst_12970__$1);
return statearr_13105;
})();var statearr_13106_13185 = state_13086__$1;(statearr_13106_13185[(2)] = null);
(statearr_13106_13185[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (21)))
{var inst_13008 = (state_13086[(2)]);var state_13086__$1 = state_13086;var statearr_13110_13186 = state_13086__$1;(statearr_13110_13186[(2)] = inst_13008);
(statearr_13110_13186[(1)] = (18));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (31)))
{var inst_13034 = (state_13086[(12)]);var inst_13038 = done.call(null,null);var inst_13039 = cljs.core.async.untap_STAR_.call(null,m,inst_13034);var state_13086__$1 = (function (){var statearr_13111 = state_13086;(statearr_13111[(19)] = inst_13038);
return statearr_13111;
})();var statearr_13112_13187 = state_13086__$1;(statearr_13112_13187[(2)] = inst_13039);
(statearr_13112_13187[(1)] = (32));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (32)))
{var inst_13026 = (state_13086[(20)]);var inst_13028 = (state_13086[(21)]);var inst_13027 = (state_13086[(9)]);var inst_13029 = (state_13086[(10)]);var inst_13041 = (state_13086[(2)]);var inst_13042 = (inst_13029 + (1));var tmp13107 = inst_13026;var tmp13108 = inst_13028;var tmp13109 = inst_13027;var inst_13026__$1 = tmp13107;var inst_13027__$1 = tmp13109;var inst_13028__$1 = tmp13108;var inst_13029__$1 = inst_13042;var state_13086__$1 = (function (){var statearr_13113 = state_13086;(statearr_13113[(22)] = inst_13041);
(statearr_13113[(20)] = inst_13026__$1);
(statearr_13113[(21)] = inst_13028__$1);
(statearr_13113[(9)] = inst_13027__$1);
(statearr_13113[(10)] = inst_13029__$1);
return statearr_13113;
})();var statearr_13114_13188 = state_13086__$1;(statearr_13114_13188[(2)] = null);
(statearr_13114_13188[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (40)))
{var inst_13054 = (state_13086[(23)]);var inst_13058 = done.call(null,null);var inst_13059 = cljs.core.async.untap_STAR_.call(null,m,inst_13054);var state_13086__$1 = (function (){var statearr_13115 = state_13086;(statearr_13115[(24)] = inst_13058);
return statearr_13115;
})();var statearr_13116_13189 = state_13086__$1;(statearr_13116_13189[(2)] = inst_13059);
(statearr_13116_13189[(1)] = (41));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (33)))
{var inst_13045 = (state_13086[(25)]);var inst_13047 = cljs.core.chunked_seq_QMARK_.call(null,inst_13045);var state_13086__$1 = state_13086;if(inst_13047)
{var statearr_13117_13190 = state_13086__$1;(statearr_13117_13190[(1)] = (36));
} else
{var statearr_13118_13191 = state_13086__$1;(statearr_13118_13191[(1)] = (37));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (13)))
{var inst_12977 = (state_13086[(26)]);var inst_12980 = cljs.core.async.close_BANG_.call(null,inst_12977);var state_13086__$1 = state_13086;var statearr_13119_13192 = state_13086__$1;(statearr_13119_13192[(2)] = inst_12980);
(statearr_13119_13192[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (22)))
{var inst_12998 = (state_13086[(8)]);var inst_13001 = cljs.core.async.close_BANG_.call(null,inst_12998);var state_13086__$1 = state_13086;var statearr_13120_13193 = state_13086__$1;(statearr_13120_13193[(2)] = inst_13001);
(statearr_13120_13193[(1)] = (24));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (36)))
{var inst_13045 = (state_13086[(25)]);var inst_13049 = cljs.core.chunk_first.call(null,inst_13045);var inst_13050 = cljs.core.chunk_rest.call(null,inst_13045);var inst_13051 = cljs.core.count.call(null,inst_13049);var inst_13026 = inst_13050;var inst_13027 = inst_13049;var inst_13028 = inst_13051;var inst_13029 = (0);var state_13086__$1 = (function (){var statearr_13121 = state_13086;(statearr_13121[(20)] = inst_13026);
(statearr_13121[(21)] = inst_13028);
(statearr_13121[(9)] = inst_13027);
(statearr_13121[(10)] = inst_13029);
return statearr_13121;
})();var statearr_13122_13194 = state_13086__$1;(statearr_13122_13194[(2)] = null);
(statearr_13122_13194[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (41)))
{var inst_13045 = (state_13086[(25)]);var inst_13061 = (state_13086[(2)]);var inst_13062 = cljs.core.next.call(null,inst_13045);var inst_13026 = inst_13062;var inst_13027 = null;var inst_13028 = (0);var inst_13029 = (0);var state_13086__$1 = (function (){var statearr_13123 = state_13086;(statearr_13123[(20)] = inst_13026);
(statearr_13123[(21)] = inst_13028);
(statearr_13123[(9)] = inst_13027);
(statearr_13123[(10)] = inst_13029);
(statearr_13123[(27)] = inst_13061);
return statearr_13123;
})();var statearr_13124_13195 = state_13086__$1;(statearr_13124_13195[(2)] = null);
(statearr_13124_13195[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (43)))
{var state_13086__$1 = state_13086;var statearr_13125_13196 = state_13086__$1;(statearr_13125_13196[(2)] = null);
(statearr_13125_13196[(1)] = (44));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (29)))
{var inst_13070 = (state_13086[(2)]);var state_13086__$1 = state_13086;var statearr_13126_13197 = state_13086__$1;(statearr_13126_13197[(2)] = inst_13070);
(statearr_13126_13197[(1)] = (26));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (44)))
{var inst_13079 = (state_13086[(2)]);var state_13086__$1 = (function (){var statearr_13127 = state_13086;(statearr_13127[(28)] = inst_13079);
return statearr_13127;
})();var statearr_13128_13198 = state_13086__$1;(statearr_13128_13198[(2)] = null);
(statearr_13128_13198[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (6)))
{var inst_13018 = (state_13086[(29)]);var inst_13017 = cljs.core.deref.call(null,cs);var inst_13018__$1 = cljs.core.keys.call(null,inst_13017);var inst_13019 = cljs.core.count.call(null,inst_13018__$1);var inst_13020 = cljs.core.reset_BANG_.call(null,dctr,inst_13019);var inst_13025 = cljs.core.seq.call(null,inst_13018__$1);var inst_13026 = inst_13025;var inst_13027 = null;var inst_13028 = (0);var inst_13029 = (0);var state_13086__$1 = (function (){var statearr_13129 = state_13086;(statearr_13129[(20)] = inst_13026);
(statearr_13129[(21)] = inst_13028);
(statearr_13129[(9)] = inst_13027);
(statearr_13129[(30)] = inst_13020);
(statearr_13129[(10)] = inst_13029);
(statearr_13129[(29)] = inst_13018__$1);
return statearr_13129;
})();var statearr_13130_13199 = state_13086__$1;(statearr_13130_13199[(2)] = null);
(statearr_13130_13199[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (28)))
{var inst_13026 = (state_13086[(20)]);var inst_13045 = (state_13086[(25)]);var inst_13045__$1 = cljs.core.seq.call(null,inst_13026);var state_13086__$1 = (function (){var statearr_13131 = state_13086;(statearr_13131[(25)] = inst_13045__$1);
return statearr_13131;
})();if(inst_13045__$1)
{var statearr_13132_13200 = state_13086__$1;(statearr_13132_13200[(1)] = (33));
} else
{var statearr_13133_13201 = state_13086__$1;(statearr_13133_13201[(1)] = (34));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (25)))
{var inst_13028 = (state_13086[(21)]);var inst_13029 = (state_13086[(10)]);var inst_13031 = (inst_13029 < inst_13028);var inst_13032 = inst_13031;var state_13086__$1 = state_13086;if(cljs.core.truth_(inst_13032))
{var statearr_13134_13202 = state_13086__$1;(statearr_13134_13202[(1)] = (27));
} else
{var statearr_13135_13203 = state_13086__$1;(statearr_13135_13203[(1)] = (28));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (34)))
{var state_13086__$1 = state_13086;var statearr_13136_13204 = state_13086__$1;(statearr_13136_13204[(2)] = null);
(statearr_13136_13204[(1)] = (35));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (17)))
{var state_13086__$1 = state_13086;var statearr_13137_13205 = state_13086__$1;(statearr_13137_13205[(2)] = null);
(statearr_13137_13205[(1)] = (18));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (3)))
{var inst_13084 = (state_13086[(2)]);var state_13086__$1 = state_13086;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13086__$1,inst_13084);
} else
{if((state_val_13087 === (12)))
{var inst_13013 = (state_13086[(2)]);var state_13086__$1 = state_13086;var statearr_13138_13206 = state_13086__$1;(statearr_13138_13206[(2)] = inst_13013);
(statearr_13138_13206[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (2)))
{var state_13086__$1 = state_13086;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13086__$1,(4),ch);
} else
{if((state_val_13087 === (23)))
{var state_13086__$1 = state_13086;var statearr_13139_13207 = state_13086__$1;(statearr_13139_13207[(2)] = null);
(statearr_13139_13207[(1)] = (24));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (35)))
{var inst_13068 = (state_13086[(2)]);var state_13086__$1 = state_13086;var statearr_13140_13208 = state_13086__$1;(statearr_13140_13208[(2)] = inst_13068);
(statearr_13140_13208[(1)] = (29));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (19)))
{var inst_12987 = (state_13086[(7)]);var inst_12991 = cljs.core.chunk_first.call(null,inst_12987);var inst_12992 = cljs.core.chunk_rest.call(null,inst_12987);var inst_12993 = cljs.core.count.call(null,inst_12991);var inst_12967 = inst_12992;var inst_12968 = inst_12991;var inst_12969 = inst_12993;var inst_12970 = (0);var state_13086__$1 = (function (){var statearr_13141 = state_13086;(statearr_13141[(13)] = inst_12969);
(statearr_13141[(14)] = inst_12967);
(statearr_13141[(16)] = inst_12968);
(statearr_13141[(17)] = inst_12970);
return statearr_13141;
})();var statearr_13142_13209 = state_13086__$1;(statearr_13142_13209[(2)] = null);
(statearr_13142_13209[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (11)))
{var inst_12967 = (state_13086[(14)]);var inst_12987 = (state_13086[(7)]);var inst_12987__$1 = cljs.core.seq.call(null,inst_12967);var state_13086__$1 = (function (){var statearr_13143 = state_13086;(statearr_13143[(7)] = inst_12987__$1);
return statearr_13143;
})();if(inst_12987__$1)
{var statearr_13144_13210 = state_13086__$1;(statearr_13144_13210[(1)] = (16));
} else
{var statearr_13145_13211 = state_13086__$1;(statearr_13145_13211[(1)] = (17));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (9)))
{var inst_13015 = (state_13086[(2)]);var state_13086__$1 = state_13086;var statearr_13146_13212 = state_13086__$1;(statearr_13146_13212[(2)] = inst_13015);
(statearr_13146_13212[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (5)))
{var inst_12965 = cljs.core.deref.call(null,cs);var inst_12966 = cljs.core.seq.call(null,inst_12965);var inst_12967 = inst_12966;var inst_12968 = null;var inst_12969 = (0);var inst_12970 = (0);var state_13086__$1 = (function (){var statearr_13147 = state_13086;(statearr_13147[(13)] = inst_12969);
(statearr_13147[(14)] = inst_12967);
(statearr_13147[(16)] = inst_12968);
(statearr_13147[(17)] = inst_12970);
return statearr_13147;
})();var statearr_13148_13213 = state_13086__$1;(statearr_13148_13213[(2)] = null);
(statearr_13148_13213[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (14)))
{var state_13086__$1 = state_13086;var statearr_13149_13214 = state_13086__$1;(statearr_13149_13214[(2)] = null);
(statearr_13149_13214[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (45)))
{var inst_13076 = (state_13086[(2)]);var state_13086__$1 = state_13086;var statearr_13150_13215 = state_13086__$1;(statearr_13150_13215[(2)] = inst_13076);
(statearr_13150_13215[(1)] = (44));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (26)))
{var inst_13018 = (state_13086[(29)]);var inst_13072 = (state_13086[(2)]);var inst_13073 = cljs.core.seq.call(null,inst_13018);var state_13086__$1 = (function (){var statearr_13151 = state_13086;(statearr_13151[(31)] = inst_13072);
return statearr_13151;
})();if(inst_13073)
{var statearr_13152_13216 = state_13086__$1;(statearr_13152_13216[(1)] = (42));
} else
{var statearr_13153_13217 = state_13086__$1;(statearr_13153_13217[(1)] = (43));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (16)))
{var inst_12987 = (state_13086[(7)]);var inst_12989 = cljs.core.chunked_seq_QMARK_.call(null,inst_12987);var state_13086__$1 = state_13086;if(inst_12989)
{var statearr_13154_13218 = state_13086__$1;(statearr_13154_13218[(1)] = (19));
} else
{var statearr_13155_13219 = state_13086__$1;(statearr_13155_13219[(1)] = (20));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (38)))
{var inst_13065 = (state_13086[(2)]);var state_13086__$1 = state_13086;var statearr_13156_13220 = state_13086__$1;(statearr_13156_13220[(2)] = inst_13065);
(statearr_13156_13220[(1)] = (35));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (30)))
{var state_13086__$1 = state_13086;var statearr_13157_13221 = state_13086__$1;(statearr_13157_13221[(2)] = null);
(statearr_13157_13221[(1)] = (32));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (10)))
{var inst_12968 = (state_13086[(16)]);var inst_12970 = (state_13086[(17)]);var inst_12976 = cljs.core._nth.call(null,inst_12968,inst_12970);var inst_12977 = cljs.core.nth.call(null,inst_12976,(0),null);var inst_12978 = cljs.core.nth.call(null,inst_12976,(1),null);var state_13086__$1 = (function (){var statearr_13158 = state_13086;(statearr_13158[(26)] = inst_12977);
return statearr_13158;
})();if(cljs.core.truth_(inst_12978))
{var statearr_13159_13222 = state_13086__$1;(statearr_13159_13222[(1)] = (13));
} else
{var statearr_13160_13223 = state_13086__$1;(statearr_13160_13223[(1)] = (14));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (18)))
{var inst_13011 = (state_13086[(2)]);var state_13086__$1 = state_13086;var statearr_13161_13224 = state_13086__$1;(statearr_13161_13224[(2)] = inst_13011);
(statearr_13161_13224[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (42)))
{var state_13086__$1 = state_13086;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13086__$1,(45),dchan);
} else
{if((state_val_13087 === (37)))
{var inst_13054 = (state_13086[(23)]);var inst_13045 = (state_13086[(25)]);var inst_12958 = (state_13086[(11)]);var inst_13054__$1 = cljs.core.first.call(null,inst_13045);var inst_13055 = cljs.core.async.put_BANG_.call(null,inst_13054__$1,inst_12958,done);var state_13086__$1 = (function (){var statearr_13162 = state_13086;(statearr_13162[(23)] = inst_13054__$1);
return statearr_13162;
})();if(cljs.core.truth_(inst_13055))
{var statearr_13163_13225 = state_13086__$1;(statearr_13163_13225[(1)] = (39));
} else
{var statearr_13164_13226 = state_13086__$1;(statearr_13164_13226[(1)] = (40));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13087 === (8)))
{var inst_12969 = (state_13086[(13)]);var inst_12970 = (state_13086[(17)]);var inst_12972 = (inst_12970 < inst_12969);var inst_12973 = inst_12972;var state_13086__$1 = state_13086;if(cljs.core.truth_(inst_12973))
{var statearr_13165_13227 = state_13086__$1;(statearr_13165_13227[(1)] = (10));
} else
{var statearr_13166_13228 = state_13086__$1;(statearr_13166_13228[(1)] = (11));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___13174,cs,m,dchan,dctr,done))
;return ((function (switch__6404__auto__,c__6419__auto___13174,cs,m,dchan,dctr,done){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_13170 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_13170[(0)] = state_machine__6405__auto__);
(statearr_13170[(1)] = (1));
return statearr_13170;
});
var state_machine__6405__auto____1 = (function (state_13086){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_13086);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e13171){if((e13171 instanceof Object))
{var ex__6408__auto__ = e13171;var statearr_13172_13229 = state_13086;(statearr_13172_13229[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13086);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13171;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13230 = state_13086;
state_13086 = G__13230;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_13086){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_13086);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___13174,cs,m,dchan,dctr,done))
})();var state__6421__auto__ = (function (){var statearr_13173 = f__6420__auto__.call(null);(statearr_13173[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___13174);
return statearr_13173;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___13174,cs,m,dchan,dctr,done))
);
return m;
});
/**
* Copies the mult source onto the supplied channel.
* 
* By default the channel will be closed when the source closes,
* but can be determined by the close? parameter.
*/
cljs.core.async.tap = (function() {
var tap = null;
var tap__2 = (function (mult,ch){return tap.call(null,mult,ch,true);
});
var tap__3 = (function (mult,ch,close_QMARK_){cljs.core.async.tap_STAR_.call(null,mult,ch,close_QMARK_);
return ch;
});
tap = function(mult,ch,close_QMARK_){
switch(arguments.length){
case 2:
return tap__2.call(this,mult,ch);
case 3:
return tap__3.call(this,mult,ch,close_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
tap.cljs$core$IFn$_invoke$arity$2 = tap__2;
tap.cljs$core$IFn$_invoke$arity$3 = tap__3;
return tap;
})()
;
/**
* Disconnects a target channel from a mult
*/
cljs.core.async.untap = (function untap(mult,ch){return cljs.core.async.untap_STAR_.call(null,mult,ch);
});
/**
* Disconnects all target channels from a mult
*/
cljs.core.async.untap_all = (function untap_all(mult){return cljs.core.async.untap_all_STAR_.call(null,mult);
});
cljs.core.async.Mix = (function (){var obj13232 = {};return obj13232;
})();
cljs.core.async.admix_STAR_ = (function admix_STAR_(m,ch){if((function (){var and__3573__auto__ = m;if(and__3573__auto__)
{return m.cljs$core$async$Mix$admix_STAR_$arity$2;
} else
{return and__3573__auto__;
}
})())
{return m.cljs$core$async$Mix$admix_STAR_$arity$2(m,ch);
} else
{var x__4221__auto__ = (((m == null))?null:m);return (function (){var or__3585__auto__ = (cljs.core.async.admix_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.admix_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.admix*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.unmix_STAR_ = (function unmix_STAR_(m,ch){if((function (){var and__3573__auto__ = m;if(and__3573__auto__)
{return m.cljs$core$async$Mix$unmix_STAR_$arity$2;
} else
{return and__3573__auto__;
}
})())
{return m.cljs$core$async$Mix$unmix_STAR_$arity$2(m,ch);
} else
{var x__4221__auto__ = (((m == null))?null:m);return (function (){var or__3585__auto__ = (cljs.core.async.unmix_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.unmix_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.unmix*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.unmix_all_STAR_ = (function unmix_all_STAR_(m){if((function (){var and__3573__auto__ = m;if(and__3573__auto__)
{return m.cljs$core$async$Mix$unmix_all_STAR_$arity$1;
} else
{return and__3573__auto__;
}
})())
{return m.cljs$core$async$Mix$unmix_all_STAR_$arity$1(m);
} else
{var x__4221__auto__ = (((m == null))?null:m);return (function (){var or__3585__auto__ = (cljs.core.async.unmix_all_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.unmix_all_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.unmix-all*",m);
}
}
})().call(null,m);
}
});
cljs.core.async.toggle_STAR_ = (function toggle_STAR_(m,state_map){if((function (){var and__3573__auto__ = m;if(and__3573__auto__)
{return m.cljs$core$async$Mix$toggle_STAR_$arity$2;
} else
{return and__3573__auto__;
}
})())
{return m.cljs$core$async$Mix$toggle_STAR_$arity$2(m,state_map);
} else
{var x__4221__auto__ = (((m == null))?null:m);return (function (){var or__3585__auto__ = (cljs.core.async.toggle_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.toggle_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.toggle*",m);
}
}
})().call(null,m,state_map);
}
});
cljs.core.async.solo_mode_STAR_ = (function solo_mode_STAR_(m,mode){if((function (){var and__3573__auto__ = m;if(and__3573__auto__)
{return m.cljs$core$async$Mix$solo_mode_STAR_$arity$2;
} else
{return and__3573__auto__;
}
})())
{return m.cljs$core$async$Mix$solo_mode_STAR_$arity$2(m,mode);
} else
{var x__4221__auto__ = (((m == null))?null:m);return (function (){var or__3585__auto__ = (cljs.core.async.solo_mode_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.solo_mode_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.solo-mode*",m);
}
}
})().call(null,m,mode);
}
});
/**
* Creates and returns a mix of one or more input channels which will
* be put on the supplied out channel. Input sources can be added to
* the mix with 'admix', and removed with 'unmix'. A mix supports
* soloing, muting and pausing multiple inputs atomically using
* 'toggle', and can solo using either muting or pausing as determined
* by 'solo-mode'.
* 
* Each channel can have zero or more boolean modes set via 'toggle':
* 
* :solo - when true, only this (ond other soloed) channel(s) will appear
* in the mix output channel. :mute and :pause states of soloed
* channels are ignored. If solo-mode is :mute, non-soloed
* channels are muted, if :pause, non-soloed channels are
* paused.
* 
* :mute - muted channels will have their contents consumed but not included in the mix
* :pause - paused channels will not have their contents consumed (and thus also not included in the mix)
*/
cljs.core.async.mix = (function mix(out){var cs = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);var solo_modes = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"pause","pause",-2095325672),null,new cljs.core.Keyword(null,"mute","mute",1151223646),null], null), null);var attrs = cljs.core.conj.call(null,solo_modes,new cljs.core.Keyword(null,"solo","solo",-316350075));var solo_mode = cljs.core.atom.call(null,new cljs.core.Keyword(null,"mute","mute",1151223646));var change = cljs.core.async.chan.call(null);var changed = ((function (cs,solo_modes,attrs,solo_mode,change){
return (function (){return cljs.core.async.put_BANG_.call(null,change,true);
});})(cs,solo_modes,attrs,solo_mode,change))
;var pick = ((function (cs,solo_modes,attrs,solo_mode,change,changed){
return (function (attr,chs){return cljs.core.reduce_kv.call(null,((function (cs,solo_modes,attrs,solo_mode,change,changed){
return (function (ret,c,v){if(cljs.core.truth_(attr.call(null,v)))
{return cljs.core.conj.call(null,ret,c);
} else
{return ret;
}
});})(cs,solo_modes,attrs,solo_mode,change,changed))
,cljs.core.PersistentHashSet.EMPTY,chs);
});})(cs,solo_modes,attrs,solo_mode,change,changed))
;var calc_state = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick){
return (function (){var chs = cljs.core.deref.call(null,cs);var mode = cljs.core.deref.call(null,solo_mode);var solos = pick.call(null,new cljs.core.Keyword(null,"solo","solo",-316350075),chs);var pauses = pick.call(null,new cljs.core.Keyword(null,"pause","pause",-2095325672),chs);return new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"solos","solos",1441458643),solos,new cljs.core.Keyword(null,"mutes","mutes",1068806309),pick.call(null,new cljs.core.Keyword(null,"mute","mute",1151223646),chs),new cljs.core.Keyword(null,"reads","reads",-1215067361),cljs.core.conj.call(null,(((cljs.core._EQ_.call(null,mode,new cljs.core.Keyword(null,"pause","pause",-2095325672))) && (!(cljs.core.empty_QMARK_.call(null,solos))))?cljs.core.vec.call(null,solos):cljs.core.vec.call(null,cljs.core.remove.call(null,pauses,cljs.core.keys.call(null,chs)))),change)], null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick))
;var m = (function (){if(typeof cljs.core.async.t13352 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t13352 = (function (change,mix,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,meta13353){
this.change = change;
this.mix = mix;
this.solo_mode = solo_mode;
this.pick = pick;
this.cs = cs;
this.calc_state = calc_state;
this.out = out;
this.changed = changed;
this.solo_modes = solo_modes;
this.attrs = attrs;
this.meta13353 = meta13353;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t13352.cljs$lang$type = true;
cljs.core.async.t13352.cljs$lang$ctorStr = "cljs.core.async/t13352";
cljs.core.async.t13352.cljs$lang$ctorPrWriter = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"cljs.core.async/t13352");
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t13352.prototype.cljs$core$async$Mix$ = true;
cljs.core.async.t13352.prototype.cljs$core$async$Mix$admix_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,ch){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.assoc,ch,cljs.core.PersistentArrayMap.EMPTY);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t13352.prototype.cljs$core$async$Mix$unmix_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,ch){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.dissoc,ch);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t13352.prototype.cljs$core$async$Mix$unmix_all_STAR_$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_){var self__ = this;
var ___$1 = this;cljs.core.reset_BANG_.call(null,self__.cs,cljs.core.PersistentArrayMap.EMPTY);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t13352.prototype.cljs$core$async$Mix$toggle_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,state_map){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.partial.call(null,cljs.core.merge_with,cljs.core.merge),state_map);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t13352.prototype.cljs$core$async$Mix$solo_mode_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,mode){var self__ = this;
var ___$1 = this;if(cljs.core.truth_(self__.solo_modes.call(null,mode)))
{} else
{throw (new Error(("Assert failed: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(("mode must be one of: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(self__.solo_modes)))+"\n"+cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.pr_str.call(null,cljs.core.list(new cljs.core.Symbol(null,"solo-modes","solo-modes",882180540,null),new cljs.core.Symbol(null,"mode","mode",-2000032078,null)))))));
}
cljs.core.reset_BANG_.call(null,self__.solo_mode,mode);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t13352.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t13352.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_){var self__ = this;
var ___$1 = this;return self__.out;
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t13352.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_13354){var self__ = this;
var _13354__$1 = this;return self__.meta13353;
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t13352.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_13354,meta13353__$1){var self__ = this;
var _13354__$1 = this;return (new cljs.core.async.t13352(self__.change,self__.mix,self__.solo_mode,self__.pick,self__.cs,self__.calc_state,self__.out,self__.changed,self__.solo_modes,self__.attrs,meta13353__$1));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.__GT_t13352 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function __GT_t13352(change__$1,mix__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta13353){return (new cljs.core.async.t13352(change__$1,mix__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta13353));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
}
return (new cljs.core.async.t13352(change,mix,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,null));
})();var c__6419__auto___13471 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___13471,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___13471,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function (state_13424){var state_val_13425 = (state_13424[(1)]);if((state_val_13425 === (7)))
{var inst_13368 = (state_13424[(7)]);var inst_13373 = cljs.core.apply.call(null,cljs.core.hash_map,inst_13368);var state_13424__$1 = state_13424;var statearr_13426_13472 = state_13424__$1;(statearr_13426_13472[(2)] = inst_13373);
(statearr_13426_13472[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (20)))
{var inst_13383 = (state_13424[(8)]);var state_13424__$1 = state_13424;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13424__$1,(23),out,inst_13383);
} else
{if((state_val_13425 === (1)))
{var inst_13358 = (state_13424[(9)]);var inst_13358__$1 = calc_state.call(null);var inst_13359 = cljs.core.seq_QMARK_.call(null,inst_13358__$1);var state_13424__$1 = (function (){var statearr_13427 = state_13424;(statearr_13427[(9)] = inst_13358__$1);
return statearr_13427;
})();if(inst_13359)
{var statearr_13428_13473 = state_13424__$1;(statearr_13428_13473[(1)] = (2));
} else
{var statearr_13429_13474 = state_13424__$1;(statearr_13429_13474[(1)] = (3));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (24)))
{var inst_13376 = (state_13424[(10)]);var inst_13368 = inst_13376;var state_13424__$1 = (function (){var statearr_13430 = state_13424;(statearr_13430[(7)] = inst_13368);
return statearr_13430;
})();var statearr_13431_13475 = state_13424__$1;(statearr_13431_13475[(2)] = null);
(statearr_13431_13475[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (4)))
{var inst_13358 = (state_13424[(9)]);var inst_13364 = (state_13424[(2)]);var inst_13365 = cljs.core.get.call(null,inst_13364,new cljs.core.Keyword(null,"reads","reads",-1215067361));var inst_13366 = cljs.core.get.call(null,inst_13364,new cljs.core.Keyword(null,"mutes","mutes",1068806309));var inst_13367 = cljs.core.get.call(null,inst_13364,new cljs.core.Keyword(null,"solos","solos",1441458643));var inst_13368 = inst_13358;var state_13424__$1 = (function (){var statearr_13432 = state_13424;(statearr_13432[(11)] = inst_13366);
(statearr_13432[(12)] = inst_13367);
(statearr_13432[(13)] = inst_13365);
(statearr_13432[(7)] = inst_13368);
return statearr_13432;
})();var statearr_13433_13476 = state_13424__$1;(statearr_13433_13476[(2)] = null);
(statearr_13433_13476[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (15)))
{var state_13424__$1 = state_13424;var statearr_13434_13477 = state_13424__$1;(statearr_13434_13477[(2)] = null);
(statearr_13434_13477[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (21)))
{var inst_13376 = (state_13424[(10)]);var inst_13368 = inst_13376;var state_13424__$1 = (function (){var statearr_13435 = state_13424;(statearr_13435[(7)] = inst_13368);
return statearr_13435;
})();var statearr_13436_13478 = state_13424__$1;(statearr_13436_13478[(2)] = null);
(statearr_13436_13478[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (13)))
{var inst_13420 = (state_13424[(2)]);var state_13424__$1 = state_13424;var statearr_13437_13479 = state_13424__$1;(statearr_13437_13479[(2)] = inst_13420);
(statearr_13437_13479[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (22)))
{var inst_13418 = (state_13424[(2)]);var state_13424__$1 = state_13424;var statearr_13438_13480 = state_13424__$1;(statearr_13438_13480[(2)] = inst_13418);
(statearr_13438_13480[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (6)))
{var inst_13422 = (state_13424[(2)]);var state_13424__$1 = state_13424;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13424__$1,inst_13422);
} else
{if((state_val_13425 === (25)))
{var state_13424__$1 = state_13424;var statearr_13439_13481 = state_13424__$1;(statearr_13439_13481[(2)] = null);
(statearr_13439_13481[(1)] = (26));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (17)))
{var inst_13398 = (state_13424[(14)]);var state_13424__$1 = state_13424;var statearr_13440_13482 = state_13424__$1;(statearr_13440_13482[(2)] = inst_13398);
(statearr_13440_13482[(1)] = (19));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (3)))
{var inst_13358 = (state_13424[(9)]);var state_13424__$1 = state_13424;var statearr_13441_13483 = state_13424__$1;(statearr_13441_13483[(2)] = inst_13358);
(statearr_13441_13483[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (12)))
{var inst_13384 = (state_13424[(15)]);var inst_13379 = (state_13424[(16)]);var inst_13398 = (state_13424[(14)]);var inst_13398__$1 = inst_13379.call(null,inst_13384);var state_13424__$1 = (function (){var statearr_13442 = state_13424;(statearr_13442[(14)] = inst_13398__$1);
return statearr_13442;
})();if(cljs.core.truth_(inst_13398__$1))
{var statearr_13443_13484 = state_13424__$1;(statearr_13443_13484[(1)] = (17));
} else
{var statearr_13444_13485 = state_13424__$1;(statearr_13444_13485[(1)] = (18));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (2)))
{var inst_13358 = (state_13424[(9)]);var inst_13361 = cljs.core.apply.call(null,cljs.core.hash_map,inst_13358);var state_13424__$1 = state_13424;var statearr_13445_13486 = state_13424__$1;(statearr_13445_13486[(2)] = inst_13361);
(statearr_13445_13486[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (23)))
{var inst_13409 = (state_13424[(2)]);var state_13424__$1 = state_13424;if(cljs.core.truth_(inst_13409))
{var statearr_13446_13487 = state_13424__$1;(statearr_13446_13487[(1)] = (24));
} else
{var statearr_13447_13488 = state_13424__$1;(statearr_13447_13488[(1)] = (25));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (19)))
{var inst_13406 = (state_13424[(2)]);var state_13424__$1 = state_13424;if(cljs.core.truth_(inst_13406))
{var statearr_13448_13489 = state_13424__$1;(statearr_13448_13489[(1)] = (20));
} else
{var statearr_13449_13490 = state_13424__$1;(statearr_13449_13490[(1)] = (21));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (11)))
{var inst_13383 = (state_13424[(8)]);var inst_13389 = (inst_13383 == null);var state_13424__$1 = state_13424;if(cljs.core.truth_(inst_13389))
{var statearr_13450_13491 = state_13424__$1;(statearr_13450_13491[(1)] = (14));
} else
{var statearr_13451_13492 = state_13424__$1;(statearr_13451_13492[(1)] = (15));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (9)))
{var inst_13376 = (state_13424[(10)]);var inst_13376__$1 = (state_13424[(2)]);var inst_13377 = cljs.core.get.call(null,inst_13376__$1,new cljs.core.Keyword(null,"reads","reads",-1215067361));var inst_13378 = cljs.core.get.call(null,inst_13376__$1,new cljs.core.Keyword(null,"mutes","mutes",1068806309));var inst_13379 = cljs.core.get.call(null,inst_13376__$1,new cljs.core.Keyword(null,"solos","solos",1441458643));var state_13424__$1 = (function (){var statearr_13452 = state_13424;(statearr_13452[(16)] = inst_13379);
(statearr_13452[(10)] = inst_13376__$1);
(statearr_13452[(17)] = inst_13378);
return statearr_13452;
})();return cljs.core.async.impl.ioc_helpers.ioc_alts_BANG_.call(null,state_13424__$1,(10),inst_13377);
} else
{if((state_val_13425 === (5)))
{var inst_13368 = (state_13424[(7)]);var inst_13371 = cljs.core.seq_QMARK_.call(null,inst_13368);var state_13424__$1 = state_13424;if(inst_13371)
{var statearr_13453_13493 = state_13424__$1;(statearr_13453_13493[(1)] = (7));
} else
{var statearr_13454_13494 = state_13424__$1;(statearr_13454_13494[(1)] = (8));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (14)))
{var inst_13384 = (state_13424[(15)]);var inst_13391 = cljs.core.swap_BANG_.call(null,cs,cljs.core.dissoc,inst_13384);var state_13424__$1 = state_13424;var statearr_13455_13495 = state_13424__$1;(statearr_13455_13495[(2)] = inst_13391);
(statearr_13455_13495[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (26)))
{var inst_13414 = (state_13424[(2)]);var state_13424__$1 = state_13424;var statearr_13456_13496 = state_13424__$1;(statearr_13456_13496[(2)] = inst_13414);
(statearr_13456_13496[(1)] = (22));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (16)))
{var inst_13394 = (state_13424[(2)]);var inst_13395 = calc_state.call(null);var inst_13368 = inst_13395;var state_13424__$1 = (function (){var statearr_13457 = state_13424;(statearr_13457[(18)] = inst_13394);
(statearr_13457[(7)] = inst_13368);
return statearr_13457;
})();var statearr_13458_13497 = state_13424__$1;(statearr_13458_13497[(2)] = null);
(statearr_13458_13497[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (10)))
{var inst_13384 = (state_13424[(15)]);var inst_13383 = (state_13424[(8)]);var inst_13382 = (state_13424[(2)]);var inst_13383__$1 = cljs.core.nth.call(null,inst_13382,(0),null);var inst_13384__$1 = cljs.core.nth.call(null,inst_13382,(1),null);var inst_13385 = (inst_13383__$1 == null);var inst_13386 = cljs.core._EQ_.call(null,inst_13384__$1,change);var inst_13387 = (inst_13385) || (inst_13386);var state_13424__$1 = (function (){var statearr_13459 = state_13424;(statearr_13459[(15)] = inst_13384__$1);
(statearr_13459[(8)] = inst_13383__$1);
return statearr_13459;
})();if(cljs.core.truth_(inst_13387))
{var statearr_13460_13498 = state_13424__$1;(statearr_13460_13498[(1)] = (11));
} else
{var statearr_13461_13499 = state_13424__$1;(statearr_13461_13499[(1)] = (12));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (18)))
{var inst_13384 = (state_13424[(15)]);var inst_13379 = (state_13424[(16)]);var inst_13378 = (state_13424[(17)]);var inst_13401 = cljs.core.empty_QMARK_.call(null,inst_13379);var inst_13402 = inst_13378.call(null,inst_13384);var inst_13403 = cljs.core.not.call(null,inst_13402);var inst_13404 = (inst_13401) && (inst_13403);var state_13424__$1 = state_13424;var statearr_13462_13500 = state_13424__$1;(statearr_13462_13500[(2)] = inst_13404);
(statearr_13462_13500[(1)] = (19));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13425 === (8)))
{var inst_13368 = (state_13424[(7)]);var state_13424__$1 = state_13424;var statearr_13463_13501 = state_13424__$1;(statearr_13463_13501[(2)] = inst_13368);
(statearr_13463_13501[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___13471,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
;return ((function (switch__6404__auto__,c__6419__auto___13471,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_13467 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_13467[(0)] = state_machine__6405__auto__);
(statearr_13467[(1)] = (1));
return statearr_13467;
});
var state_machine__6405__auto____1 = (function (state_13424){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_13424);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e13468){if((e13468 instanceof Object))
{var ex__6408__auto__ = e13468;var statearr_13469_13502 = state_13424;(statearr_13469_13502[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13424);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13468;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13503 = state_13424;
state_13424 = G__13503;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_13424){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_13424);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___13471,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
})();var state__6421__auto__ = (function (){var statearr_13470 = f__6420__auto__.call(null);(statearr_13470[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___13471);
return statearr_13470;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___13471,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
);
return m;
});
/**
* Adds ch as an input to the mix
*/
cljs.core.async.admix = (function admix(mix,ch){return cljs.core.async.admix_STAR_.call(null,mix,ch);
});
/**
* Removes ch as an input to the mix
*/
cljs.core.async.unmix = (function unmix(mix,ch){return cljs.core.async.unmix_STAR_.call(null,mix,ch);
});
/**
* removes all inputs from the mix
*/
cljs.core.async.unmix_all = (function unmix_all(mix){return cljs.core.async.unmix_all_STAR_.call(null,mix);
});
/**
* Atomically sets the state(s) of one or more channels in a mix. The
* state map is a map of channels -> channel-state-map. A
* channel-state-map is a map of attrs -> boolean, where attr is one or
* more of :mute, :pause or :solo. Any states supplied are merged with
* the current state.
* 
* Note that channels can be added to a mix via toggle, which can be
* used to add channels in a particular (e.g. paused) state.
*/
cljs.core.async.toggle = (function toggle(mix,state_map){return cljs.core.async.toggle_STAR_.call(null,mix,state_map);
});
/**
* Sets the solo mode of the mix. mode must be one of :mute or :pause
*/
cljs.core.async.solo_mode = (function solo_mode(mix,mode){return cljs.core.async.solo_mode_STAR_.call(null,mix,mode);
});
cljs.core.async.Pub = (function (){var obj13505 = {};return obj13505;
})();
cljs.core.async.sub_STAR_ = (function sub_STAR_(p,v,ch,close_QMARK_){if((function (){var and__3573__auto__ = p;if(and__3573__auto__)
{return p.cljs$core$async$Pub$sub_STAR_$arity$4;
} else
{return and__3573__auto__;
}
})())
{return p.cljs$core$async$Pub$sub_STAR_$arity$4(p,v,ch,close_QMARK_);
} else
{var x__4221__auto__ = (((p == null))?null:p);return (function (){var or__3585__auto__ = (cljs.core.async.sub_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.sub_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Pub.sub*",p);
}
}
})().call(null,p,v,ch,close_QMARK_);
}
});
cljs.core.async.unsub_STAR_ = (function unsub_STAR_(p,v,ch){if((function (){var and__3573__auto__ = p;if(and__3573__auto__)
{return p.cljs$core$async$Pub$unsub_STAR_$arity$3;
} else
{return and__3573__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_STAR_$arity$3(p,v,ch);
} else
{var x__4221__auto__ = (((p == null))?null:p);return (function (){var or__3585__auto__ = (cljs.core.async.unsub_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.unsub_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Pub.unsub*",p);
}
}
})().call(null,p,v,ch);
}
});
cljs.core.async.unsub_all_STAR_ = (function() {
var unsub_all_STAR_ = null;
var unsub_all_STAR___1 = (function (p){if((function (){var and__3573__auto__ = p;if(and__3573__auto__)
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$1;
} else
{return and__3573__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$1(p);
} else
{var x__4221__auto__ = (((p == null))?null:p);return (function (){var or__3585__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.unsub_all_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Pub.unsub-all*",p);
}
}
})().call(null,p);
}
});
var unsub_all_STAR___2 = (function (p,v){if((function (){var and__3573__auto__ = p;if(and__3573__auto__)
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$2;
} else
{return and__3573__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$2(p,v);
} else
{var x__4221__auto__ = (((p == null))?null:p);return (function (){var or__3585__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__4221__auto__)]);if(or__3585__auto__)
{return or__3585__auto__;
} else
{var or__3585__auto____$1 = (cljs.core.async.unsub_all_STAR_["_"]);if(or__3585__auto____$1)
{return or__3585__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Pub.unsub-all*",p);
}
}
})().call(null,p,v);
}
});
unsub_all_STAR_ = function(p,v){
switch(arguments.length){
case 1:
return unsub_all_STAR___1.call(this,p);
case 2:
return unsub_all_STAR___2.call(this,p,v);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
unsub_all_STAR_.cljs$core$IFn$_invoke$arity$1 = unsub_all_STAR___1;
unsub_all_STAR_.cljs$core$IFn$_invoke$arity$2 = unsub_all_STAR___2;
return unsub_all_STAR_;
})()
;
/**
* Creates and returns a pub(lication) of the supplied channel,
* partitioned into topics by the topic-fn. topic-fn will be applied to
* each value on the channel and the result will determine the 'topic'
* on which that value will be put. Channels can be subscribed to
* receive copies of topics using 'sub', and unsubscribed using
* 'unsub'. Each topic will be handled by an internal mult on a
* dedicated channel. By default these internal channels are
* unbuffered, but a buf-fn can be supplied which, given a topic,
* creates a buffer with desired properties.
* 
* Each item is distributed to all subs in parallel and synchronously,
* i.e. each sub must accept before the next item is distributed. Use
* buffering/windowing to prevent slow subs from holding up the pub.
* 
* Items received when there are no matching subs get dropped.
* 
* Note that if buf-fns are used then each topic is handled
* asynchronously, i.e. if a channel is subscribed to more than one
* topic it should not expect them to be interleaved identically with
* the source.
*/
cljs.core.async.pub = (function() {
var pub = null;
var pub__2 = (function (ch,topic_fn){return pub.call(null,ch,topic_fn,cljs.core.constantly.call(null,null));
});
var pub__3 = (function (ch,topic_fn,buf_fn){var mults = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);var ensure_mult = ((function (mults){
return (function (topic){var or__3585__auto__ = cljs.core.get.call(null,cljs.core.deref.call(null,mults),topic);if(cljs.core.truth_(or__3585__auto__))
{return or__3585__auto__;
} else
{return cljs.core.get.call(null,cljs.core.swap_BANG_.call(null,mults,((function (or__3585__auto__,mults){
return (function (p1__13506_SHARP_){if(cljs.core.truth_(p1__13506_SHARP_.call(null,topic)))
{return p1__13506_SHARP_;
} else
{return cljs.core.assoc.call(null,p1__13506_SHARP_,topic,cljs.core.async.mult.call(null,cljs.core.async.chan.call(null,buf_fn.call(null,topic))));
}
});})(or__3585__auto__,mults))
),topic);
}
});})(mults))
;var p = (function (){if(typeof cljs.core.async.t13629 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t13629 = (function (ensure_mult,mults,buf_fn,topic_fn,ch,pub,meta13630){
this.ensure_mult = ensure_mult;
this.mults = mults;
this.buf_fn = buf_fn;
this.topic_fn = topic_fn;
this.ch = ch;
this.pub = pub;
this.meta13630 = meta13630;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t13629.cljs$lang$type = true;
cljs.core.async.t13629.cljs$lang$ctorStr = "cljs.core.async/t13629";
cljs.core.async.t13629.cljs$lang$ctorPrWriter = ((function (mults,ensure_mult){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"cljs.core.async/t13629");
});})(mults,ensure_mult))
;
cljs.core.async.t13629.prototype.cljs$core$async$Pub$ = true;
cljs.core.async.t13629.prototype.cljs$core$async$Pub$sub_STAR_$arity$4 = ((function (mults,ensure_mult){
return (function (p,topic,ch__$2,close_QMARK_){var self__ = this;
var p__$1 = this;var m = self__.ensure_mult.call(null,topic);return cljs.core.async.tap.call(null,m,ch__$2,close_QMARK_);
});})(mults,ensure_mult))
;
cljs.core.async.t13629.prototype.cljs$core$async$Pub$unsub_STAR_$arity$3 = ((function (mults,ensure_mult){
return (function (p,topic,ch__$2){var self__ = this;
var p__$1 = this;var temp__4126__auto__ = cljs.core.get.call(null,cljs.core.deref.call(null,self__.mults),topic);if(cljs.core.truth_(temp__4126__auto__))
{var m = temp__4126__auto__;return cljs.core.async.untap.call(null,m,ch__$2);
} else
{return null;
}
});})(mults,ensure_mult))
;
cljs.core.async.t13629.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$1 = ((function (mults,ensure_mult){
return (function (_){var self__ = this;
var ___$1 = this;return cljs.core.reset_BANG_.call(null,self__.mults,cljs.core.PersistentArrayMap.EMPTY);
});})(mults,ensure_mult))
;
cljs.core.async.t13629.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$2 = ((function (mults,ensure_mult){
return (function (_,topic){var self__ = this;
var ___$1 = this;return cljs.core.swap_BANG_.call(null,self__.mults,cljs.core.dissoc,topic);
});})(mults,ensure_mult))
;
cljs.core.async.t13629.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t13629.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (mults,ensure_mult){
return (function (_){var self__ = this;
var ___$1 = this;return self__.ch;
});})(mults,ensure_mult))
;
cljs.core.async.t13629.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (mults,ensure_mult){
return (function (_13631){var self__ = this;
var _13631__$1 = this;return self__.meta13630;
});})(mults,ensure_mult))
;
cljs.core.async.t13629.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (mults,ensure_mult){
return (function (_13631,meta13630__$1){var self__ = this;
var _13631__$1 = this;return (new cljs.core.async.t13629(self__.ensure_mult,self__.mults,self__.buf_fn,self__.topic_fn,self__.ch,self__.pub,meta13630__$1));
});})(mults,ensure_mult))
;
cljs.core.async.__GT_t13629 = ((function (mults,ensure_mult){
return (function __GT_t13629(ensure_mult__$1,mults__$1,buf_fn__$1,topic_fn__$1,ch__$1,pub__$1,meta13630){return (new cljs.core.async.t13629(ensure_mult__$1,mults__$1,buf_fn__$1,topic_fn__$1,ch__$1,pub__$1,meta13630));
});})(mults,ensure_mult))
;
}
return (new cljs.core.async.t13629(ensure_mult,mults,buf_fn,topic_fn,ch,pub,null));
})();var c__6419__auto___13751 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___13751,mults,ensure_mult,p){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___13751,mults,ensure_mult,p){
return (function (state_13703){var state_val_13704 = (state_13703[(1)]);if((state_val_13704 === (7)))
{var inst_13699 = (state_13703[(2)]);var state_13703__$1 = state_13703;var statearr_13705_13752 = state_13703__$1;(statearr_13705_13752[(2)] = inst_13699);
(statearr_13705_13752[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (20)))
{var state_13703__$1 = state_13703;var statearr_13706_13753 = state_13703__$1;(statearr_13706_13753[(2)] = null);
(statearr_13706_13753[(1)] = (21));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (1)))
{var state_13703__$1 = state_13703;var statearr_13707_13754 = state_13703__$1;(statearr_13707_13754[(2)] = null);
(statearr_13707_13754[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (24)))
{var inst_13682 = (state_13703[(7)]);var inst_13691 = cljs.core.swap_BANG_.call(null,mults,cljs.core.dissoc,inst_13682);var state_13703__$1 = state_13703;var statearr_13708_13755 = state_13703__$1;(statearr_13708_13755[(2)] = inst_13691);
(statearr_13708_13755[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (4)))
{var inst_13634 = (state_13703[(8)]);var inst_13634__$1 = (state_13703[(2)]);var inst_13635 = (inst_13634__$1 == null);var state_13703__$1 = (function (){var statearr_13709 = state_13703;(statearr_13709[(8)] = inst_13634__$1);
return statearr_13709;
})();if(cljs.core.truth_(inst_13635))
{var statearr_13710_13756 = state_13703__$1;(statearr_13710_13756[(1)] = (5));
} else
{var statearr_13711_13757 = state_13703__$1;(statearr_13711_13757[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (15)))
{var inst_13676 = (state_13703[(2)]);var state_13703__$1 = state_13703;var statearr_13712_13758 = state_13703__$1;(statearr_13712_13758[(2)] = inst_13676);
(statearr_13712_13758[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (21)))
{var inst_13696 = (state_13703[(2)]);var state_13703__$1 = (function (){var statearr_13713 = state_13703;(statearr_13713[(9)] = inst_13696);
return statearr_13713;
})();var statearr_13714_13759 = state_13703__$1;(statearr_13714_13759[(2)] = null);
(statearr_13714_13759[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (13)))
{var inst_13658 = (state_13703[(10)]);var inst_13660 = cljs.core.chunked_seq_QMARK_.call(null,inst_13658);var state_13703__$1 = state_13703;if(inst_13660)
{var statearr_13715_13760 = state_13703__$1;(statearr_13715_13760[(1)] = (16));
} else
{var statearr_13716_13761 = state_13703__$1;(statearr_13716_13761[(1)] = (17));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (22)))
{var inst_13688 = (state_13703[(2)]);var state_13703__$1 = state_13703;if(cljs.core.truth_(inst_13688))
{var statearr_13717_13762 = state_13703__$1;(statearr_13717_13762[(1)] = (23));
} else
{var statearr_13718_13763 = state_13703__$1;(statearr_13718_13763[(1)] = (24));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (6)))
{var inst_13684 = (state_13703[(11)]);var inst_13634 = (state_13703[(8)]);var inst_13682 = (state_13703[(7)]);var inst_13682__$1 = topic_fn.call(null,inst_13634);var inst_13683 = cljs.core.deref.call(null,mults);var inst_13684__$1 = cljs.core.get.call(null,inst_13683,inst_13682__$1);var state_13703__$1 = (function (){var statearr_13719 = state_13703;(statearr_13719[(11)] = inst_13684__$1);
(statearr_13719[(7)] = inst_13682__$1);
return statearr_13719;
})();if(cljs.core.truth_(inst_13684__$1))
{var statearr_13720_13764 = state_13703__$1;(statearr_13720_13764[(1)] = (19));
} else
{var statearr_13721_13765 = state_13703__$1;(statearr_13721_13765[(1)] = (20));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (25)))
{var inst_13693 = (state_13703[(2)]);var state_13703__$1 = state_13703;var statearr_13722_13766 = state_13703__$1;(statearr_13722_13766[(2)] = inst_13693);
(statearr_13722_13766[(1)] = (21));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (17)))
{var inst_13658 = (state_13703[(10)]);var inst_13667 = cljs.core.first.call(null,inst_13658);var inst_13668 = cljs.core.async.muxch_STAR_.call(null,inst_13667);var inst_13669 = cljs.core.async.close_BANG_.call(null,inst_13668);var inst_13670 = cljs.core.next.call(null,inst_13658);var inst_13644 = inst_13670;var inst_13645 = null;var inst_13646 = (0);var inst_13647 = (0);var state_13703__$1 = (function (){var statearr_13723 = state_13703;(statearr_13723[(12)] = inst_13645);
(statearr_13723[(13)] = inst_13647);
(statearr_13723[(14)] = inst_13646);
(statearr_13723[(15)] = inst_13644);
(statearr_13723[(16)] = inst_13669);
return statearr_13723;
})();var statearr_13724_13767 = state_13703__$1;(statearr_13724_13767[(2)] = null);
(statearr_13724_13767[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (3)))
{var inst_13701 = (state_13703[(2)]);var state_13703__$1 = state_13703;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13703__$1,inst_13701);
} else
{if((state_val_13704 === (12)))
{var inst_13678 = (state_13703[(2)]);var state_13703__$1 = state_13703;var statearr_13725_13768 = state_13703__$1;(statearr_13725_13768[(2)] = inst_13678);
(statearr_13725_13768[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (2)))
{var state_13703__$1 = state_13703;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13703__$1,(4),ch);
} else
{if((state_val_13704 === (23)))
{var state_13703__$1 = state_13703;var statearr_13726_13769 = state_13703__$1;(statearr_13726_13769[(2)] = null);
(statearr_13726_13769[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (19)))
{var inst_13684 = (state_13703[(11)]);var inst_13634 = (state_13703[(8)]);var inst_13686 = cljs.core.async.muxch_STAR_.call(null,inst_13684);var state_13703__$1 = state_13703;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13703__$1,(22),inst_13686,inst_13634);
} else
{if((state_val_13704 === (11)))
{var inst_13658 = (state_13703[(10)]);var inst_13644 = (state_13703[(15)]);var inst_13658__$1 = cljs.core.seq.call(null,inst_13644);var state_13703__$1 = (function (){var statearr_13727 = state_13703;(statearr_13727[(10)] = inst_13658__$1);
return statearr_13727;
})();if(inst_13658__$1)
{var statearr_13728_13770 = state_13703__$1;(statearr_13728_13770[(1)] = (13));
} else
{var statearr_13729_13771 = state_13703__$1;(statearr_13729_13771[(1)] = (14));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (9)))
{var inst_13680 = (state_13703[(2)]);var state_13703__$1 = state_13703;var statearr_13730_13772 = state_13703__$1;(statearr_13730_13772[(2)] = inst_13680);
(statearr_13730_13772[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (5)))
{var inst_13641 = cljs.core.deref.call(null,mults);var inst_13642 = cljs.core.vals.call(null,inst_13641);var inst_13643 = cljs.core.seq.call(null,inst_13642);var inst_13644 = inst_13643;var inst_13645 = null;var inst_13646 = (0);var inst_13647 = (0);var state_13703__$1 = (function (){var statearr_13731 = state_13703;(statearr_13731[(12)] = inst_13645);
(statearr_13731[(13)] = inst_13647);
(statearr_13731[(14)] = inst_13646);
(statearr_13731[(15)] = inst_13644);
return statearr_13731;
})();var statearr_13732_13773 = state_13703__$1;(statearr_13732_13773[(2)] = null);
(statearr_13732_13773[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (14)))
{var state_13703__$1 = state_13703;var statearr_13736_13774 = state_13703__$1;(statearr_13736_13774[(2)] = null);
(statearr_13736_13774[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (16)))
{var inst_13658 = (state_13703[(10)]);var inst_13662 = cljs.core.chunk_first.call(null,inst_13658);var inst_13663 = cljs.core.chunk_rest.call(null,inst_13658);var inst_13664 = cljs.core.count.call(null,inst_13662);var inst_13644 = inst_13663;var inst_13645 = inst_13662;var inst_13646 = inst_13664;var inst_13647 = (0);var state_13703__$1 = (function (){var statearr_13737 = state_13703;(statearr_13737[(12)] = inst_13645);
(statearr_13737[(13)] = inst_13647);
(statearr_13737[(14)] = inst_13646);
(statearr_13737[(15)] = inst_13644);
return statearr_13737;
})();var statearr_13738_13775 = state_13703__$1;(statearr_13738_13775[(2)] = null);
(statearr_13738_13775[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (10)))
{var inst_13645 = (state_13703[(12)]);var inst_13647 = (state_13703[(13)]);var inst_13646 = (state_13703[(14)]);var inst_13644 = (state_13703[(15)]);var inst_13652 = cljs.core._nth.call(null,inst_13645,inst_13647);var inst_13653 = cljs.core.async.muxch_STAR_.call(null,inst_13652);var inst_13654 = cljs.core.async.close_BANG_.call(null,inst_13653);var inst_13655 = (inst_13647 + (1));var tmp13733 = inst_13645;var tmp13734 = inst_13646;var tmp13735 = inst_13644;var inst_13644__$1 = tmp13735;var inst_13645__$1 = tmp13733;var inst_13646__$1 = tmp13734;var inst_13647__$1 = inst_13655;var state_13703__$1 = (function (){var statearr_13739 = state_13703;(statearr_13739[(12)] = inst_13645__$1);
(statearr_13739[(13)] = inst_13647__$1);
(statearr_13739[(14)] = inst_13646__$1);
(statearr_13739[(15)] = inst_13644__$1);
(statearr_13739[(17)] = inst_13654);
return statearr_13739;
})();var statearr_13740_13776 = state_13703__$1;(statearr_13740_13776[(2)] = null);
(statearr_13740_13776[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (18)))
{var inst_13673 = (state_13703[(2)]);var state_13703__$1 = state_13703;var statearr_13741_13777 = state_13703__$1;(statearr_13741_13777[(2)] = inst_13673);
(statearr_13741_13777[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13704 === (8)))
{var inst_13647 = (state_13703[(13)]);var inst_13646 = (state_13703[(14)]);var inst_13649 = (inst_13647 < inst_13646);var inst_13650 = inst_13649;var state_13703__$1 = state_13703;if(cljs.core.truth_(inst_13650))
{var statearr_13742_13778 = state_13703__$1;(statearr_13742_13778[(1)] = (10));
} else
{var statearr_13743_13779 = state_13703__$1;(statearr_13743_13779[(1)] = (11));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___13751,mults,ensure_mult,p))
;return ((function (switch__6404__auto__,c__6419__auto___13751,mults,ensure_mult,p){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_13747 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_13747[(0)] = state_machine__6405__auto__);
(statearr_13747[(1)] = (1));
return statearr_13747;
});
var state_machine__6405__auto____1 = (function (state_13703){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_13703);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e13748){if((e13748 instanceof Object))
{var ex__6408__auto__ = e13748;var statearr_13749_13780 = state_13703;(statearr_13749_13780[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13703);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13748;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13781 = state_13703;
state_13703 = G__13781;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_13703){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_13703);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___13751,mults,ensure_mult,p))
})();var state__6421__auto__ = (function (){var statearr_13750 = f__6420__auto__.call(null);(statearr_13750[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___13751);
return statearr_13750;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___13751,mults,ensure_mult,p))
);
return p;
});
pub = function(ch,topic_fn,buf_fn){
switch(arguments.length){
case 2:
return pub__2.call(this,ch,topic_fn);
case 3:
return pub__3.call(this,ch,topic_fn,buf_fn);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
pub.cljs$core$IFn$_invoke$arity$2 = pub__2;
pub.cljs$core$IFn$_invoke$arity$3 = pub__3;
return pub;
})()
;
/**
* Subscribes a channel to a topic of a pub.
* 
* By default the channel will be closed when the source closes,
* but can be determined by the close? parameter.
*/
cljs.core.async.sub = (function() {
var sub = null;
var sub__3 = (function (p,topic,ch){return sub.call(null,p,topic,ch,true);
});
var sub__4 = (function (p,topic,ch,close_QMARK_){return cljs.core.async.sub_STAR_.call(null,p,topic,ch,close_QMARK_);
});
sub = function(p,topic,ch,close_QMARK_){
switch(arguments.length){
case 3:
return sub__3.call(this,p,topic,ch);
case 4:
return sub__4.call(this,p,topic,ch,close_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
sub.cljs$core$IFn$_invoke$arity$3 = sub__3;
sub.cljs$core$IFn$_invoke$arity$4 = sub__4;
return sub;
})()
;
/**
* Unsubscribes a channel from a topic of a pub
*/
cljs.core.async.unsub = (function unsub(p,topic,ch){return cljs.core.async.unsub_STAR_.call(null,p,topic,ch);
});
/**
* Unsubscribes all channels from a pub, or a topic of a pub
*/
cljs.core.async.unsub_all = (function() {
var unsub_all = null;
var unsub_all__1 = (function (p){return cljs.core.async.unsub_all_STAR_.call(null,p);
});
var unsub_all__2 = (function (p,topic){return cljs.core.async.unsub_all_STAR_.call(null,p,topic);
});
unsub_all = function(p,topic){
switch(arguments.length){
case 1:
return unsub_all__1.call(this,p);
case 2:
return unsub_all__2.call(this,p,topic);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
unsub_all.cljs$core$IFn$_invoke$arity$1 = unsub_all__1;
unsub_all.cljs$core$IFn$_invoke$arity$2 = unsub_all__2;
return unsub_all;
})()
;
/**
* Takes a function and a collection of source channels, and returns a
* channel which contains the values produced by applying f to the set
* of first items taken from each source channel, followed by applying
* f to the set of second items from each channel, until any one of the
* channels is closed, at which point the output channel will be
* closed. The returned channel will be unbuffered by default, or a
* buf-or-n can be supplied
*/
cljs.core.async.map = (function() {
var map = null;
var map__2 = (function (f,chs){return map.call(null,f,chs,null);
});
var map__3 = (function (f,chs,buf_or_n){var chs__$1 = cljs.core.vec.call(null,chs);var out = cljs.core.async.chan.call(null,buf_or_n);var cnt = cljs.core.count.call(null,chs__$1);var rets = cljs.core.object_array.call(null,cnt);var dchan = cljs.core.async.chan.call(null,(1));var dctr = cljs.core.atom.call(null,null);var done = cljs.core.mapv.call(null,((function (chs__$1,out,cnt,rets,dchan,dctr){
return (function (i){return ((function (chs__$1,out,cnt,rets,dchan,dctr){
return (function (ret){(rets[i] = ret);
if((cljs.core.swap_BANG_.call(null,dctr,cljs.core.dec) === (0)))
{return cljs.core.async.put_BANG_.call(null,dchan,rets.slice((0)));
} else
{return null;
}
});
;})(chs__$1,out,cnt,rets,dchan,dctr))
});})(chs__$1,out,cnt,rets,dchan,dctr))
,cljs.core.range.call(null,cnt));var c__6419__auto___13918 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___13918,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___13918,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function (state_13888){var state_val_13889 = (state_13888[(1)]);if((state_val_13889 === (7)))
{var state_13888__$1 = state_13888;var statearr_13890_13919 = state_13888__$1;(statearr_13890_13919[(2)] = null);
(statearr_13890_13919[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (1)))
{var state_13888__$1 = state_13888;var statearr_13891_13920 = state_13888__$1;(statearr_13891_13920[(2)] = null);
(statearr_13891_13920[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (4)))
{var inst_13852 = (state_13888[(7)]);var inst_13854 = (inst_13852 < cnt);var state_13888__$1 = state_13888;if(cljs.core.truth_(inst_13854))
{var statearr_13892_13921 = state_13888__$1;(statearr_13892_13921[(1)] = (6));
} else
{var statearr_13893_13922 = state_13888__$1;(statearr_13893_13922[(1)] = (7));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (15)))
{var inst_13884 = (state_13888[(2)]);var state_13888__$1 = state_13888;var statearr_13894_13923 = state_13888__$1;(statearr_13894_13923[(2)] = inst_13884);
(statearr_13894_13923[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (13)))
{var inst_13877 = cljs.core.async.close_BANG_.call(null,out);var state_13888__$1 = state_13888;var statearr_13895_13924 = state_13888__$1;(statearr_13895_13924[(2)] = inst_13877);
(statearr_13895_13924[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (6)))
{var state_13888__$1 = state_13888;var statearr_13896_13925 = state_13888__$1;(statearr_13896_13925[(2)] = null);
(statearr_13896_13925[(1)] = (11));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (3)))
{var inst_13886 = (state_13888[(2)]);var state_13888__$1 = state_13888;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13888__$1,inst_13886);
} else
{if((state_val_13889 === (12)))
{var inst_13874 = (state_13888[(8)]);var inst_13874__$1 = (state_13888[(2)]);var inst_13875 = cljs.core.some.call(null,cljs.core.nil_QMARK_,inst_13874__$1);var state_13888__$1 = (function (){var statearr_13897 = state_13888;(statearr_13897[(8)] = inst_13874__$1);
return statearr_13897;
})();if(cljs.core.truth_(inst_13875))
{var statearr_13898_13926 = state_13888__$1;(statearr_13898_13926[(1)] = (13));
} else
{var statearr_13899_13927 = state_13888__$1;(statearr_13899_13927[(1)] = (14));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (2)))
{var inst_13851 = cljs.core.reset_BANG_.call(null,dctr,cnt);var inst_13852 = (0);var state_13888__$1 = (function (){var statearr_13900 = state_13888;(statearr_13900[(9)] = inst_13851);
(statearr_13900[(7)] = inst_13852);
return statearr_13900;
})();var statearr_13901_13928 = state_13888__$1;(statearr_13901_13928[(2)] = null);
(statearr_13901_13928[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (11)))
{var inst_13852 = (state_13888[(7)]);var _ = cljs.core.async.impl.ioc_helpers.add_exception_frame.call(null,state_13888,(10),Object,null,(9));var inst_13861 = chs__$1.call(null,inst_13852);var inst_13862 = done.call(null,inst_13852);var inst_13863 = cljs.core.async.take_BANG_.call(null,inst_13861,inst_13862);var state_13888__$1 = state_13888;var statearr_13902_13929 = state_13888__$1;(statearr_13902_13929[(2)] = inst_13863);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13888__$1);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (9)))
{var inst_13852 = (state_13888[(7)]);var inst_13865 = (state_13888[(2)]);var inst_13866 = (inst_13852 + (1));var inst_13852__$1 = inst_13866;var state_13888__$1 = (function (){var statearr_13903 = state_13888;(statearr_13903[(7)] = inst_13852__$1);
(statearr_13903[(10)] = inst_13865);
return statearr_13903;
})();var statearr_13904_13930 = state_13888__$1;(statearr_13904_13930[(2)] = null);
(statearr_13904_13930[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (5)))
{var inst_13872 = (state_13888[(2)]);var state_13888__$1 = (function (){var statearr_13905 = state_13888;(statearr_13905[(11)] = inst_13872);
return statearr_13905;
})();return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13888__$1,(12),dchan);
} else
{if((state_val_13889 === (14)))
{var inst_13874 = (state_13888[(8)]);var inst_13879 = cljs.core.apply.call(null,f,inst_13874);var state_13888__$1 = state_13888;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13888__$1,(16),out,inst_13879);
} else
{if((state_val_13889 === (16)))
{var inst_13881 = (state_13888[(2)]);var state_13888__$1 = (function (){var statearr_13906 = state_13888;(statearr_13906[(12)] = inst_13881);
return statearr_13906;
})();var statearr_13907_13931 = state_13888__$1;(statearr_13907_13931[(2)] = null);
(statearr_13907_13931[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (10)))
{var inst_13856 = (state_13888[(2)]);var inst_13857 = cljs.core.swap_BANG_.call(null,dctr,cljs.core.dec);var state_13888__$1 = (function (){var statearr_13908 = state_13888;(statearr_13908[(13)] = inst_13856);
return statearr_13908;
})();var statearr_13909_13932 = state_13888__$1;(statearr_13909_13932[(2)] = inst_13857);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13888__$1);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13889 === (8)))
{var inst_13870 = (state_13888[(2)]);var state_13888__$1 = state_13888;var statearr_13910_13933 = state_13888__$1;(statearr_13910_13933[(2)] = inst_13870);
(statearr_13910_13933[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___13918,chs__$1,out,cnt,rets,dchan,dctr,done))
;return ((function (switch__6404__auto__,c__6419__auto___13918,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_13914 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_13914[(0)] = state_machine__6405__auto__);
(statearr_13914[(1)] = (1));
return statearr_13914;
});
var state_machine__6405__auto____1 = (function (state_13888){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_13888);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e13915){if((e13915 instanceof Object))
{var ex__6408__auto__ = e13915;var statearr_13916_13934 = state_13888;(statearr_13916_13934[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13888);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13915;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13935 = state_13888;
state_13888 = G__13935;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_13888){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_13888);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___13918,chs__$1,out,cnt,rets,dchan,dctr,done))
})();var state__6421__auto__ = (function (){var statearr_13917 = f__6420__auto__.call(null);(statearr_13917[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___13918);
return statearr_13917;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___13918,chs__$1,out,cnt,rets,dchan,dctr,done))
);
return out;
});
map = function(f,chs,buf_or_n){
switch(arguments.length){
case 2:
return map__2.call(this,f,chs);
case 3:
return map__3.call(this,f,chs,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
map.cljs$core$IFn$_invoke$arity$2 = map__2;
map.cljs$core$IFn$_invoke$arity$3 = map__3;
return map;
})()
;
/**
* Takes a collection of source channels and returns a channel which
* contains all values taken from them. The returned channel will be
* unbuffered by default, or a buf-or-n can be supplied. The channel
* will close after all the source channels have closed.
*/
cljs.core.async.merge = (function() {
var merge = null;
var merge__1 = (function (chs){return merge.call(null,chs,null);
});
var merge__2 = (function (chs,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__6419__auto___14043 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___14043,out){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___14043,out){
return (function (state_14019){var state_val_14020 = (state_14019[(1)]);if((state_val_14020 === (7)))
{var inst_13998 = (state_14019[(7)]);var inst_13999 = (state_14019[(8)]);var inst_13998__$1 = (state_14019[(2)]);var inst_13999__$1 = cljs.core.nth.call(null,inst_13998__$1,(0),null);var inst_14000 = cljs.core.nth.call(null,inst_13998__$1,(1),null);var inst_14001 = (inst_13999__$1 == null);var state_14019__$1 = (function (){var statearr_14021 = state_14019;(statearr_14021[(9)] = inst_14000);
(statearr_14021[(7)] = inst_13998__$1);
(statearr_14021[(8)] = inst_13999__$1);
return statearr_14021;
})();if(cljs.core.truth_(inst_14001))
{var statearr_14022_14044 = state_14019__$1;(statearr_14022_14044[(1)] = (8));
} else
{var statearr_14023_14045 = state_14019__$1;(statearr_14023_14045[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14020 === (1)))
{var inst_13990 = cljs.core.vec.call(null,chs);var inst_13991 = inst_13990;var state_14019__$1 = (function (){var statearr_14024 = state_14019;(statearr_14024[(10)] = inst_13991);
return statearr_14024;
})();var statearr_14025_14046 = state_14019__$1;(statearr_14025_14046[(2)] = null);
(statearr_14025_14046[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14020 === (4)))
{var inst_13991 = (state_14019[(10)]);var state_14019__$1 = state_14019;return cljs.core.async.impl.ioc_helpers.ioc_alts_BANG_.call(null,state_14019__$1,(7),inst_13991);
} else
{if((state_val_14020 === (6)))
{var inst_14015 = (state_14019[(2)]);var state_14019__$1 = state_14019;var statearr_14026_14047 = state_14019__$1;(statearr_14026_14047[(2)] = inst_14015);
(statearr_14026_14047[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14020 === (3)))
{var inst_14017 = (state_14019[(2)]);var state_14019__$1 = state_14019;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_14019__$1,inst_14017);
} else
{if((state_val_14020 === (2)))
{var inst_13991 = (state_14019[(10)]);var inst_13993 = cljs.core.count.call(null,inst_13991);var inst_13994 = (inst_13993 > (0));var state_14019__$1 = state_14019;if(cljs.core.truth_(inst_13994))
{var statearr_14028_14048 = state_14019__$1;(statearr_14028_14048[(1)] = (4));
} else
{var statearr_14029_14049 = state_14019__$1;(statearr_14029_14049[(1)] = (5));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14020 === (11)))
{var inst_13991 = (state_14019[(10)]);var inst_14008 = (state_14019[(2)]);var tmp14027 = inst_13991;var inst_13991__$1 = tmp14027;var state_14019__$1 = (function (){var statearr_14030 = state_14019;(statearr_14030[(11)] = inst_14008);
(statearr_14030[(10)] = inst_13991__$1);
return statearr_14030;
})();var statearr_14031_14050 = state_14019__$1;(statearr_14031_14050[(2)] = null);
(statearr_14031_14050[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14020 === (9)))
{var inst_13999 = (state_14019[(8)]);var state_14019__$1 = state_14019;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14019__$1,(11),out,inst_13999);
} else
{if((state_val_14020 === (5)))
{var inst_14013 = cljs.core.async.close_BANG_.call(null,out);var state_14019__$1 = state_14019;var statearr_14032_14051 = state_14019__$1;(statearr_14032_14051[(2)] = inst_14013);
(statearr_14032_14051[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14020 === (10)))
{var inst_14011 = (state_14019[(2)]);var state_14019__$1 = state_14019;var statearr_14033_14052 = state_14019__$1;(statearr_14033_14052[(2)] = inst_14011);
(statearr_14033_14052[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14020 === (8)))
{var inst_14000 = (state_14019[(9)]);var inst_13998 = (state_14019[(7)]);var inst_13999 = (state_14019[(8)]);var inst_13991 = (state_14019[(10)]);var inst_14003 = (function (){var c = inst_14000;var v = inst_13999;var vec__13996 = inst_13998;var cs = inst_13991;return ((function (c,v,vec__13996,cs,inst_14000,inst_13998,inst_13999,inst_13991,state_val_14020,c__6419__auto___14043,out){
return (function (p1__13936_SHARP_){return cljs.core.not_EQ_.call(null,c,p1__13936_SHARP_);
});
;})(c,v,vec__13996,cs,inst_14000,inst_13998,inst_13999,inst_13991,state_val_14020,c__6419__auto___14043,out))
})();var inst_14004 = cljs.core.filterv.call(null,inst_14003,inst_13991);var inst_13991__$1 = inst_14004;var state_14019__$1 = (function (){var statearr_14034 = state_14019;(statearr_14034[(10)] = inst_13991__$1);
return statearr_14034;
})();var statearr_14035_14053 = state_14019__$1;(statearr_14035_14053[(2)] = null);
(statearr_14035_14053[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___14043,out))
;return ((function (switch__6404__auto__,c__6419__auto___14043,out){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_14039 = [null,null,null,null,null,null,null,null,null,null,null,null];(statearr_14039[(0)] = state_machine__6405__auto__);
(statearr_14039[(1)] = (1));
return statearr_14039;
});
var state_machine__6405__auto____1 = (function (state_14019){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_14019);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e14040){if((e14040 instanceof Object))
{var ex__6408__auto__ = e14040;var statearr_14041_14054 = state_14019;(statearr_14041_14054[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_14019);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e14040;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__14055 = state_14019;
state_14019 = G__14055;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_14019){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_14019);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___14043,out))
})();var state__6421__auto__ = (function (){var statearr_14042 = f__6420__auto__.call(null);(statearr_14042[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___14043);
return statearr_14042;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___14043,out))
);
return out;
});
merge = function(chs,buf_or_n){
switch(arguments.length){
case 1:
return merge__1.call(this,chs);
case 2:
return merge__2.call(this,chs,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
merge.cljs$core$IFn$_invoke$arity$1 = merge__1;
merge.cljs$core$IFn$_invoke$arity$2 = merge__2;
return merge;
})()
;
/**
* Returns a channel containing the single (collection) result of the
* items taken from the channel conjoined to the supplied
* collection. ch must close before into produces a result.
*/
cljs.core.async.into = (function into(coll,ch){return cljs.core.async.reduce.call(null,cljs.core.conj,coll,ch);
});
/**
* Returns a channel that will return, at most, n items from ch. After n items
* have been returned, or ch has been closed, the return chanel will close.
* 
* The output channel is unbuffered by default, unless buf-or-n is given.
*/
cljs.core.async.take = (function() {
var take = null;
var take__2 = (function (n,ch){return take.call(null,n,ch,null);
});
var take__3 = (function (n,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__6419__auto___14148 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___14148,out){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___14148,out){
return (function (state_14125){var state_val_14126 = (state_14125[(1)]);if((state_val_14126 === (7)))
{var inst_14107 = (state_14125[(7)]);var inst_14107__$1 = (state_14125[(2)]);var inst_14108 = (inst_14107__$1 == null);var inst_14109 = cljs.core.not.call(null,inst_14108);var state_14125__$1 = (function (){var statearr_14127 = state_14125;(statearr_14127[(7)] = inst_14107__$1);
return statearr_14127;
})();if(inst_14109)
{var statearr_14128_14149 = state_14125__$1;(statearr_14128_14149[(1)] = (8));
} else
{var statearr_14129_14150 = state_14125__$1;(statearr_14129_14150[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14126 === (1)))
{var inst_14102 = (0);var state_14125__$1 = (function (){var statearr_14130 = state_14125;(statearr_14130[(8)] = inst_14102);
return statearr_14130;
})();var statearr_14131_14151 = state_14125__$1;(statearr_14131_14151[(2)] = null);
(statearr_14131_14151[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14126 === (4)))
{var state_14125__$1 = state_14125;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_14125__$1,(7),ch);
} else
{if((state_val_14126 === (6)))
{var inst_14120 = (state_14125[(2)]);var state_14125__$1 = state_14125;var statearr_14132_14152 = state_14125__$1;(statearr_14132_14152[(2)] = inst_14120);
(statearr_14132_14152[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14126 === (3)))
{var inst_14122 = (state_14125[(2)]);var inst_14123 = cljs.core.async.close_BANG_.call(null,out);var state_14125__$1 = (function (){var statearr_14133 = state_14125;(statearr_14133[(9)] = inst_14122);
return statearr_14133;
})();return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_14125__$1,inst_14123);
} else
{if((state_val_14126 === (2)))
{var inst_14102 = (state_14125[(8)]);var inst_14104 = (inst_14102 < n);var state_14125__$1 = state_14125;if(cljs.core.truth_(inst_14104))
{var statearr_14134_14153 = state_14125__$1;(statearr_14134_14153[(1)] = (4));
} else
{var statearr_14135_14154 = state_14125__$1;(statearr_14135_14154[(1)] = (5));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14126 === (11)))
{var inst_14102 = (state_14125[(8)]);var inst_14112 = (state_14125[(2)]);var inst_14113 = (inst_14102 + (1));var inst_14102__$1 = inst_14113;var state_14125__$1 = (function (){var statearr_14136 = state_14125;(statearr_14136[(8)] = inst_14102__$1);
(statearr_14136[(10)] = inst_14112);
return statearr_14136;
})();var statearr_14137_14155 = state_14125__$1;(statearr_14137_14155[(2)] = null);
(statearr_14137_14155[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14126 === (9)))
{var state_14125__$1 = state_14125;var statearr_14138_14156 = state_14125__$1;(statearr_14138_14156[(2)] = null);
(statearr_14138_14156[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14126 === (5)))
{var state_14125__$1 = state_14125;var statearr_14139_14157 = state_14125__$1;(statearr_14139_14157[(2)] = null);
(statearr_14139_14157[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14126 === (10)))
{var inst_14117 = (state_14125[(2)]);var state_14125__$1 = state_14125;var statearr_14140_14158 = state_14125__$1;(statearr_14140_14158[(2)] = inst_14117);
(statearr_14140_14158[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14126 === (8)))
{var inst_14107 = (state_14125[(7)]);var state_14125__$1 = state_14125;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14125__$1,(11),out,inst_14107);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___14148,out))
;return ((function (switch__6404__auto__,c__6419__auto___14148,out){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_14144 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_14144[(0)] = state_machine__6405__auto__);
(statearr_14144[(1)] = (1));
return statearr_14144;
});
var state_machine__6405__auto____1 = (function (state_14125){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_14125);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e14145){if((e14145 instanceof Object))
{var ex__6408__auto__ = e14145;var statearr_14146_14159 = state_14125;(statearr_14146_14159[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_14125);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e14145;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__14160 = state_14125;
state_14125 = G__14160;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_14125){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_14125);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___14148,out))
})();var state__6421__auto__ = (function (){var statearr_14147 = f__6420__auto__.call(null);(statearr_14147[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___14148);
return statearr_14147;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___14148,out))
);
return out;
});
take = function(n,ch,buf_or_n){
switch(arguments.length){
case 2:
return take__2.call(this,n,ch);
case 3:
return take__3.call(this,n,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
take.cljs$core$IFn$_invoke$arity$2 = take__2;
take.cljs$core$IFn$_invoke$arity$3 = take__3;
return take;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.map_LT_ = (function map_LT_(f,ch){if(typeof cljs.core.async.t14168 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t14168 = (function (ch,f,map_LT_,meta14169){
this.ch = ch;
this.f = f;
this.map_LT_ = map_LT_;
this.meta14169 = meta14169;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t14168.cljs$lang$type = true;
cljs.core.async.t14168.cljs$lang$ctorStr = "cljs.core.async/t14168";
cljs.core.async.t14168.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"cljs.core.async/t14168");
});
cljs.core.async.t14168.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t14168.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.put_BANG_.call(null,self__.ch,val,fn1);
});
cljs.core.async.t14168.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t14168.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;var ret = cljs.core.async.impl.protocols.take_BANG_.call(null,self__.ch,(function (){if(typeof cljs.core.async.t14171 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t14171 = (function (fn1,_,meta14169,ch,f,map_LT_,meta14172){
this.fn1 = fn1;
this._ = _;
this.meta14169 = meta14169;
this.ch = ch;
this.f = f;
this.map_LT_ = map_LT_;
this.meta14172 = meta14172;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t14171.cljs$lang$type = true;
cljs.core.async.t14171.cljs$lang$ctorStr = "cljs.core.async/t14171";
cljs.core.async.t14171.cljs$lang$ctorPrWriter = ((function (___$1){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"cljs.core.async/t14171");
});})(___$1))
;
cljs.core.async.t14171.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t14171.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;return cljs.core.async.impl.protocols.active_QMARK_.call(null,self__.fn1);
});})(___$1))
;
cljs.core.async.t14171.prototype.cljs$core$async$impl$protocols$Handler$lock_id$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;return cljs.core.async.impl.protocols.lock_id.call(null,self__.fn1);
});})(___$1))
;
cljs.core.async.t14171.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;var f1 = cljs.core.async.impl.protocols.commit.call(null,self__.fn1);return ((function (f1,___$4,___$1){
return (function (p1__14161_SHARP_){return f1.call(null,(((p1__14161_SHARP_ == null))?null:self__.f.call(null,p1__14161_SHARP_)));
});
;})(f1,___$4,___$1))
});})(___$1))
;
cljs.core.async.t14171.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (___$1){
return (function (_14173){var self__ = this;
var _14173__$1 = this;return self__.meta14172;
});})(___$1))
;
cljs.core.async.t14171.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (___$1){
return (function (_14173,meta14172__$1){var self__ = this;
var _14173__$1 = this;return (new cljs.core.async.t14171(self__.fn1,self__._,self__.meta14169,self__.ch,self__.f,self__.map_LT_,meta14172__$1));
});})(___$1))
;
cljs.core.async.__GT_t14171 = ((function (___$1){
return (function __GT_t14171(fn1__$1,___$2,meta14169__$1,ch__$2,f__$2,map_LT___$2,meta14172){return (new cljs.core.async.t14171(fn1__$1,___$2,meta14169__$1,ch__$2,f__$2,map_LT___$2,meta14172));
});})(___$1))
;
}
return (new cljs.core.async.t14171(fn1,___$1,self__.meta14169,self__.ch,self__.f,self__.map_LT_,null));
})());if(cljs.core.truth_((function (){var and__3573__auto__ = ret;if(cljs.core.truth_(and__3573__auto__))
{return !((cljs.core.deref.call(null,ret) == null));
} else
{return and__3573__auto__;
}
})()))
{return cljs.core.async.impl.channels.box.call(null,self__.f.call(null,cljs.core.deref.call(null,ret)));
} else
{return ret;
}
});
cljs.core.async.t14168.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t14168.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_.call(null,self__.ch);
});
cljs.core.async.t14168.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.closed_QMARK_.call(null,self__.ch);
});
cljs.core.async.t14168.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_14170){var self__ = this;
var _14170__$1 = this;return self__.meta14169;
});
cljs.core.async.t14168.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_14170,meta14169__$1){var self__ = this;
var _14170__$1 = this;return (new cljs.core.async.t14168(self__.ch,self__.f,self__.map_LT_,meta14169__$1));
});
cljs.core.async.__GT_t14168 = (function __GT_t14168(ch__$1,f__$1,map_LT___$1,meta14169){return (new cljs.core.async.t14168(ch__$1,f__$1,map_LT___$1,meta14169));
});
}
return (new cljs.core.async.t14168(ch,f,map_LT_,null));
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.map_GT_ = (function map_GT_(f,ch){if(typeof cljs.core.async.t14177 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t14177 = (function (ch,f,map_GT_,meta14178){
this.ch = ch;
this.f = f;
this.map_GT_ = map_GT_;
this.meta14178 = meta14178;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t14177.cljs$lang$type = true;
cljs.core.async.t14177.cljs$lang$ctorStr = "cljs.core.async/t14177";
cljs.core.async.t14177.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"cljs.core.async/t14177");
});
cljs.core.async.t14177.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t14177.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.put_BANG_.call(null,self__.ch,self__.f.call(null,val),fn1);
});
cljs.core.async.t14177.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t14177.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.take_BANG_.call(null,self__.ch,fn1);
});
cljs.core.async.t14177.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t14177.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_.call(null,self__.ch);
});
cljs.core.async.t14177.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_14179){var self__ = this;
var _14179__$1 = this;return self__.meta14178;
});
cljs.core.async.t14177.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_14179,meta14178__$1){var self__ = this;
var _14179__$1 = this;return (new cljs.core.async.t14177(self__.ch,self__.f,self__.map_GT_,meta14178__$1));
});
cljs.core.async.__GT_t14177 = (function __GT_t14177(ch__$1,f__$1,map_GT___$1,meta14178){return (new cljs.core.async.t14177(ch__$1,f__$1,map_GT___$1,meta14178));
});
}
return (new cljs.core.async.t14177(ch,f,map_GT_,null));
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.filter_GT_ = (function filter_GT_(p,ch){if(typeof cljs.core.async.t14183 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t14183 = (function (ch,p,filter_GT_,meta14184){
this.ch = ch;
this.p = p;
this.filter_GT_ = filter_GT_;
this.meta14184 = meta14184;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t14183.cljs$lang$type = true;
cljs.core.async.t14183.cljs$lang$ctorStr = "cljs.core.async/t14183";
cljs.core.async.t14183.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"cljs.core.async/t14183");
});
cljs.core.async.t14183.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t14183.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;if(cljs.core.truth_(self__.p.call(null,val)))
{return cljs.core.async.impl.protocols.put_BANG_.call(null,self__.ch,val,fn1);
} else
{return cljs.core.async.impl.channels.box.call(null,cljs.core.not.call(null,cljs.core.async.impl.protocols.closed_QMARK_.call(null,self__.ch)));
}
});
cljs.core.async.t14183.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t14183.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.take_BANG_.call(null,self__.ch,fn1);
});
cljs.core.async.t14183.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t14183.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_.call(null,self__.ch);
});
cljs.core.async.t14183.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.closed_QMARK_.call(null,self__.ch);
});
cljs.core.async.t14183.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_14185){var self__ = this;
var _14185__$1 = this;return self__.meta14184;
});
cljs.core.async.t14183.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_14185,meta14184__$1){var self__ = this;
var _14185__$1 = this;return (new cljs.core.async.t14183(self__.ch,self__.p,self__.filter_GT_,meta14184__$1));
});
cljs.core.async.__GT_t14183 = (function __GT_t14183(ch__$1,p__$1,filter_GT___$1,meta14184){return (new cljs.core.async.t14183(ch__$1,p__$1,filter_GT___$1,meta14184));
});
}
return (new cljs.core.async.t14183(ch,p,filter_GT_,null));
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.remove_GT_ = (function remove_GT_(p,ch){return cljs.core.async.filter_GT_.call(null,cljs.core.complement.call(null,p),ch);
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.filter_LT_ = (function() {
var filter_LT_ = null;
var filter_LT___2 = (function (p,ch){return filter_LT_.call(null,p,ch,null);
});
var filter_LT___3 = (function (p,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__6419__auto___14268 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___14268,out){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___14268,out){
return (function (state_14247){var state_val_14248 = (state_14247[(1)]);if((state_val_14248 === (7)))
{var inst_14243 = (state_14247[(2)]);var state_14247__$1 = state_14247;var statearr_14249_14269 = state_14247__$1;(statearr_14249_14269[(2)] = inst_14243);
(statearr_14249_14269[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14248 === (1)))
{var state_14247__$1 = state_14247;var statearr_14250_14270 = state_14247__$1;(statearr_14250_14270[(2)] = null);
(statearr_14250_14270[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14248 === (4)))
{var inst_14229 = (state_14247[(7)]);var inst_14229__$1 = (state_14247[(2)]);var inst_14230 = (inst_14229__$1 == null);var state_14247__$1 = (function (){var statearr_14251 = state_14247;(statearr_14251[(7)] = inst_14229__$1);
return statearr_14251;
})();if(cljs.core.truth_(inst_14230))
{var statearr_14252_14271 = state_14247__$1;(statearr_14252_14271[(1)] = (5));
} else
{var statearr_14253_14272 = state_14247__$1;(statearr_14253_14272[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14248 === (6)))
{var inst_14229 = (state_14247[(7)]);var inst_14234 = p.call(null,inst_14229);var state_14247__$1 = state_14247;if(cljs.core.truth_(inst_14234))
{var statearr_14254_14273 = state_14247__$1;(statearr_14254_14273[(1)] = (8));
} else
{var statearr_14255_14274 = state_14247__$1;(statearr_14255_14274[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14248 === (3)))
{var inst_14245 = (state_14247[(2)]);var state_14247__$1 = state_14247;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_14247__$1,inst_14245);
} else
{if((state_val_14248 === (2)))
{var state_14247__$1 = state_14247;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_14247__$1,(4),ch);
} else
{if((state_val_14248 === (11)))
{var inst_14237 = (state_14247[(2)]);var state_14247__$1 = state_14247;var statearr_14256_14275 = state_14247__$1;(statearr_14256_14275[(2)] = inst_14237);
(statearr_14256_14275[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14248 === (9)))
{var state_14247__$1 = state_14247;var statearr_14257_14276 = state_14247__$1;(statearr_14257_14276[(2)] = null);
(statearr_14257_14276[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14248 === (5)))
{var inst_14232 = cljs.core.async.close_BANG_.call(null,out);var state_14247__$1 = state_14247;var statearr_14258_14277 = state_14247__$1;(statearr_14258_14277[(2)] = inst_14232);
(statearr_14258_14277[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14248 === (10)))
{var inst_14240 = (state_14247[(2)]);var state_14247__$1 = (function (){var statearr_14259 = state_14247;(statearr_14259[(8)] = inst_14240);
return statearr_14259;
})();var statearr_14260_14278 = state_14247__$1;(statearr_14260_14278[(2)] = null);
(statearr_14260_14278[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14248 === (8)))
{var inst_14229 = (state_14247[(7)]);var state_14247__$1 = state_14247;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14247__$1,(11),out,inst_14229);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___14268,out))
;return ((function (switch__6404__auto__,c__6419__auto___14268,out){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_14264 = [null,null,null,null,null,null,null,null,null];(statearr_14264[(0)] = state_machine__6405__auto__);
(statearr_14264[(1)] = (1));
return statearr_14264;
});
var state_machine__6405__auto____1 = (function (state_14247){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_14247);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e14265){if((e14265 instanceof Object))
{var ex__6408__auto__ = e14265;var statearr_14266_14279 = state_14247;(statearr_14266_14279[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_14247);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e14265;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__14280 = state_14247;
state_14247 = G__14280;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_14247){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_14247);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___14268,out))
})();var state__6421__auto__ = (function (){var statearr_14267 = f__6420__auto__.call(null);(statearr_14267[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___14268);
return statearr_14267;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___14268,out))
);
return out;
});
filter_LT_ = function(p,ch,buf_or_n){
switch(arguments.length){
case 2:
return filter_LT___2.call(this,p,ch);
case 3:
return filter_LT___3.call(this,p,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
filter_LT_.cljs$core$IFn$_invoke$arity$2 = filter_LT___2;
filter_LT_.cljs$core$IFn$_invoke$arity$3 = filter_LT___3;
return filter_LT_;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.remove_LT_ = (function() {
var remove_LT_ = null;
var remove_LT___2 = (function (p,ch){return remove_LT_.call(null,p,ch,null);
});
var remove_LT___3 = (function (p,ch,buf_or_n){return cljs.core.async.filter_LT_.call(null,cljs.core.complement.call(null,p),ch,buf_or_n);
});
remove_LT_ = function(p,ch,buf_or_n){
switch(arguments.length){
case 2:
return remove_LT___2.call(this,p,ch);
case 3:
return remove_LT___3.call(this,p,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
remove_LT_.cljs$core$IFn$_invoke$arity$2 = remove_LT___2;
remove_LT_.cljs$core$IFn$_invoke$arity$3 = remove_LT___3;
return remove_LT_;
})()
;
cljs.core.async.mapcat_STAR_ = (function mapcat_STAR_(f,in$,out){var c__6419__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto__){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto__){
return (function (state_14446){var state_val_14447 = (state_14446[(1)]);if((state_val_14447 === (7)))
{var inst_14442 = (state_14446[(2)]);var state_14446__$1 = state_14446;var statearr_14448_14489 = state_14446__$1;(statearr_14448_14489[(2)] = inst_14442);
(statearr_14448_14489[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (20)))
{var inst_14412 = (state_14446[(7)]);var inst_14423 = (state_14446[(2)]);var inst_14424 = cljs.core.next.call(null,inst_14412);var inst_14398 = inst_14424;var inst_14399 = null;var inst_14400 = (0);var inst_14401 = (0);var state_14446__$1 = (function (){var statearr_14449 = state_14446;(statearr_14449[(8)] = inst_14423);
(statearr_14449[(9)] = inst_14398);
(statearr_14449[(10)] = inst_14401);
(statearr_14449[(11)] = inst_14399);
(statearr_14449[(12)] = inst_14400);
return statearr_14449;
})();var statearr_14450_14490 = state_14446__$1;(statearr_14450_14490[(2)] = null);
(statearr_14450_14490[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (1)))
{var state_14446__$1 = state_14446;var statearr_14451_14491 = state_14446__$1;(statearr_14451_14491[(2)] = null);
(statearr_14451_14491[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (4)))
{var inst_14387 = (state_14446[(13)]);var inst_14387__$1 = (state_14446[(2)]);var inst_14388 = (inst_14387__$1 == null);var state_14446__$1 = (function (){var statearr_14452 = state_14446;(statearr_14452[(13)] = inst_14387__$1);
return statearr_14452;
})();if(cljs.core.truth_(inst_14388))
{var statearr_14453_14492 = state_14446__$1;(statearr_14453_14492[(1)] = (5));
} else
{var statearr_14454_14493 = state_14446__$1;(statearr_14454_14493[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (15)))
{var state_14446__$1 = state_14446;var statearr_14458_14494 = state_14446__$1;(statearr_14458_14494[(2)] = null);
(statearr_14458_14494[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (21)))
{var state_14446__$1 = state_14446;var statearr_14459_14495 = state_14446__$1;(statearr_14459_14495[(2)] = null);
(statearr_14459_14495[(1)] = (23));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (13)))
{var inst_14398 = (state_14446[(9)]);var inst_14401 = (state_14446[(10)]);var inst_14399 = (state_14446[(11)]);var inst_14400 = (state_14446[(12)]);var inst_14408 = (state_14446[(2)]);var inst_14409 = (inst_14401 + (1));var tmp14455 = inst_14398;var tmp14456 = inst_14399;var tmp14457 = inst_14400;var inst_14398__$1 = tmp14455;var inst_14399__$1 = tmp14456;var inst_14400__$1 = tmp14457;var inst_14401__$1 = inst_14409;var state_14446__$1 = (function (){var statearr_14460 = state_14446;(statearr_14460[(9)] = inst_14398__$1);
(statearr_14460[(10)] = inst_14401__$1);
(statearr_14460[(14)] = inst_14408);
(statearr_14460[(11)] = inst_14399__$1);
(statearr_14460[(12)] = inst_14400__$1);
return statearr_14460;
})();var statearr_14461_14496 = state_14446__$1;(statearr_14461_14496[(2)] = null);
(statearr_14461_14496[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (22)))
{var state_14446__$1 = state_14446;var statearr_14462_14497 = state_14446__$1;(statearr_14462_14497[(2)] = null);
(statearr_14462_14497[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (6)))
{var inst_14387 = (state_14446[(13)]);var inst_14396 = f.call(null,inst_14387);var inst_14397 = cljs.core.seq.call(null,inst_14396);var inst_14398 = inst_14397;var inst_14399 = null;var inst_14400 = (0);var inst_14401 = (0);var state_14446__$1 = (function (){var statearr_14463 = state_14446;(statearr_14463[(9)] = inst_14398);
(statearr_14463[(10)] = inst_14401);
(statearr_14463[(11)] = inst_14399);
(statearr_14463[(12)] = inst_14400);
return statearr_14463;
})();var statearr_14464_14498 = state_14446__$1;(statearr_14464_14498[(2)] = null);
(statearr_14464_14498[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (17)))
{var inst_14412 = (state_14446[(7)]);var inst_14416 = cljs.core.chunk_first.call(null,inst_14412);var inst_14417 = cljs.core.chunk_rest.call(null,inst_14412);var inst_14418 = cljs.core.count.call(null,inst_14416);var inst_14398 = inst_14417;var inst_14399 = inst_14416;var inst_14400 = inst_14418;var inst_14401 = (0);var state_14446__$1 = (function (){var statearr_14465 = state_14446;(statearr_14465[(9)] = inst_14398);
(statearr_14465[(10)] = inst_14401);
(statearr_14465[(11)] = inst_14399);
(statearr_14465[(12)] = inst_14400);
return statearr_14465;
})();var statearr_14466_14499 = state_14446__$1;(statearr_14466_14499[(2)] = null);
(statearr_14466_14499[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (3)))
{var inst_14444 = (state_14446[(2)]);var state_14446__$1 = state_14446;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_14446__$1,inst_14444);
} else
{if((state_val_14447 === (12)))
{var inst_14432 = (state_14446[(2)]);var state_14446__$1 = state_14446;var statearr_14467_14500 = state_14446__$1;(statearr_14467_14500[(2)] = inst_14432);
(statearr_14467_14500[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (2)))
{var state_14446__$1 = state_14446;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_14446__$1,(4),in$);
} else
{if((state_val_14447 === (23)))
{var inst_14440 = (state_14446[(2)]);var state_14446__$1 = state_14446;var statearr_14468_14501 = state_14446__$1;(statearr_14468_14501[(2)] = inst_14440);
(statearr_14468_14501[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (19)))
{var inst_14427 = (state_14446[(2)]);var state_14446__$1 = state_14446;var statearr_14469_14502 = state_14446__$1;(statearr_14469_14502[(2)] = inst_14427);
(statearr_14469_14502[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (11)))
{var inst_14398 = (state_14446[(9)]);var inst_14412 = (state_14446[(7)]);var inst_14412__$1 = cljs.core.seq.call(null,inst_14398);var state_14446__$1 = (function (){var statearr_14470 = state_14446;(statearr_14470[(7)] = inst_14412__$1);
return statearr_14470;
})();if(inst_14412__$1)
{var statearr_14471_14503 = state_14446__$1;(statearr_14471_14503[(1)] = (14));
} else
{var statearr_14472_14504 = state_14446__$1;(statearr_14472_14504[(1)] = (15));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (9)))
{var inst_14434 = (state_14446[(2)]);var inst_14435 = cljs.core.async.impl.protocols.closed_QMARK_.call(null,out);var state_14446__$1 = (function (){var statearr_14473 = state_14446;(statearr_14473[(15)] = inst_14434);
return statearr_14473;
})();if(cljs.core.truth_(inst_14435))
{var statearr_14474_14505 = state_14446__$1;(statearr_14474_14505[(1)] = (21));
} else
{var statearr_14475_14506 = state_14446__$1;(statearr_14475_14506[(1)] = (22));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (5)))
{var inst_14390 = cljs.core.async.close_BANG_.call(null,out);var state_14446__$1 = state_14446;var statearr_14476_14507 = state_14446__$1;(statearr_14476_14507[(2)] = inst_14390);
(statearr_14476_14507[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (14)))
{var inst_14412 = (state_14446[(7)]);var inst_14414 = cljs.core.chunked_seq_QMARK_.call(null,inst_14412);var state_14446__$1 = state_14446;if(inst_14414)
{var statearr_14477_14508 = state_14446__$1;(statearr_14477_14508[(1)] = (17));
} else
{var statearr_14478_14509 = state_14446__$1;(statearr_14478_14509[(1)] = (18));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (16)))
{var inst_14430 = (state_14446[(2)]);var state_14446__$1 = state_14446;var statearr_14479_14510 = state_14446__$1;(statearr_14479_14510[(2)] = inst_14430);
(statearr_14479_14510[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14447 === (10)))
{var inst_14401 = (state_14446[(10)]);var inst_14399 = (state_14446[(11)]);var inst_14406 = cljs.core._nth.call(null,inst_14399,inst_14401);var state_14446__$1 = state_14446;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14446__$1,(13),out,inst_14406);
} else
{if((state_val_14447 === (18)))
{var inst_14412 = (state_14446[(7)]);var inst_14421 = cljs.core.first.call(null,inst_14412);var state_14446__$1 = state_14446;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14446__$1,(20),out,inst_14421);
} else
{if((state_val_14447 === (8)))
{var inst_14401 = (state_14446[(10)]);var inst_14400 = (state_14446[(12)]);var inst_14403 = (inst_14401 < inst_14400);var inst_14404 = inst_14403;var state_14446__$1 = state_14446;if(cljs.core.truth_(inst_14404))
{var statearr_14480_14511 = state_14446__$1;(statearr_14480_14511[(1)] = (10));
} else
{var statearr_14481_14512 = state_14446__$1;(statearr_14481_14512[(1)] = (11));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto__))
;return ((function (switch__6404__auto__,c__6419__auto__){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_14485 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_14485[(0)] = state_machine__6405__auto__);
(statearr_14485[(1)] = (1));
return statearr_14485;
});
var state_machine__6405__auto____1 = (function (state_14446){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_14446);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e14486){if((e14486 instanceof Object))
{var ex__6408__auto__ = e14486;var statearr_14487_14513 = state_14446;(statearr_14487_14513[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_14446);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e14486;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__14514 = state_14446;
state_14446 = G__14514;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_14446){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_14446);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto__))
})();var state__6421__auto__ = (function (){var statearr_14488 = f__6420__auto__.call(null);(statearr_14488[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto__);
return statearr_14488;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto__))
);
return c__6419__auto__;
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.mapcat_LT_ = (function() {
var mapcat_LT_ = null;
var mapcat_LT___2 = (function (f,in$){return mapcat_LT_.call(null,f,in$,null);
});
var mapcat_LT___3 = (function (f,in$,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);cljs.core.async.mapcat_STAR_.call(null,f,in$,out);
return out;
});
mapcat_LT_ = function(f,in$,buf_or_n){
switch(arguments.length){
case 2:
return mapcat_LT___2.call(this,f,in$);
case 3:
return mapcat_LT___3.call(this,f,in$,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
mapcat_LT_.cljs$core$IFn$_invoke$arity$2 = mapcat_LT___2;
mapcat_LT_.cljs$core$IFn$_invoke$arity$3 = mapcat_LT___3;
return mapcat_LT_;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.mapcat_GT_ = (function() {
var mapcat_GT_ = null;
var mapcat_GT___2 = (function (f,out){return mapcat_GT_.call(null,f,out,null);
});
var mapcat_GT___3 = (function (f,out,buf_or_n){var in$ = cljs.core.async.chan.call(null,buf_or_n);cljs.core.async.mapcat_STAR_.call(null,f,in$,out);
return in$;
});
mapcat_GT_ = function(f,out,buf_or_n){
switch(arguments.length){
case 2:
return mapcat_GT___2.call(this,f,out);
case 3:
return mapcat_GT___3.call(this,f,out,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
mapcat_GT_.cljs$core$IFn$_invoke$arity$2 = mapcat_GT___2;
mapcat_GT_.cljs$core$IFn$_invoke$arity$3 = mapcat_GT___3;
return mapcat_GT_;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.unique = (function() {
var unique = null;
var unique__1 = (function (ch){return unique.call(null,ch,null);
});
var unique__2 = (function (ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__6419__auto___14611 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___14611,out){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___14611,out){
return (function (state_14586){var state_val_14587 = (state_14586[(1)]);if((state_val_14587 === (7)))
{var inst_14581 = (state_14586[(2)]);var state_14586__$1 = state_14586;var statearr_14588_14612 = state_14586__$1;(statearr_14588_14612[(2)] = inst_14581);
(statearr_14588_14612[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14587 === (1)))
{var inst_14563 = null;var state_14586__$1 = (function (){var statearr_14589 = state_14586;(statearr_14589[(7)] = inst_14563);
return statearr_14589;
})();var statearr_14590_14613 = state_14586__$1;(statearr_14590_14613[(2)] = null);
(statearr_14590_14613[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14587 === (4)))
{var inst_14566 = (state_14586[(8)]);var inst_14566__$1 = (state_14586[(2)]);var inst_14567 = (inst_14566__$1 == null);var inst_14568 = cljs.core.not.call(null,inst_14567);var state_14586__$1 = (function (){var statearr_14591 = state_14586;(statearr_14591[(8)] = inst_14566__$1);
return statearr_14591;
})();if(inst_14568)
{var statearr_14592_14614 = state_14586__$1;(statearr_14592_14614[(1)] = (5));
} else
{var statearr_14593_14615 = state_14586__$1;(statearr_14593_14615[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14587 === (6)))
{var state_14586__$1 = state_14586;var statearr_14594_14616 = state_14586__$1;(statearr_14594_14616[(2)] = null);
(statearr_14594_14616[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14587 === (3)))
{var inst_14583 = (state_14586[(2)]);var inst_14584 = cljs.core.async.close_BANG_.call(null,out);var state_14586__$1 = (function (){var statearr_14595 = state_14586;(statearr_14595[(9)] = inst_14583);
return statearr_14595;
})();return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_14586__$1,inst_14584);
} else
{if((state_val_14587 === (2)))
{var state_14586__$1 = state_14586;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_14586__$1,(4),ch);
} else
{if((state_val_14587 === (11)))
{var inst_14566 = (state_14586[(8)]);var inst_14575 = (state_14586[(2)]);var inst_14563 = inst_14566;var state_14586__$1 = (function (){var statearr_14596 = state_14586;(statearr_14596[(10)] = inst_14575);
(statearr_14596[(7)] = inst_14563);
return statearr_14596;
})();var statearr_14597_14617 = state_14586__$1;(statearr_14597_14617[(2)] = null);
(statearr_14597_14617[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14587 === (9)))
{var inst_14566 = (state_14586[(8)]);var state_14586__$1 = state_14586;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14586__$1,(11),out,inst_14566);
} else
{if((state_val_14587 === (5)))
{var inst_14563 = (state_14586[(7)]);var inst_14566 = (state_14586[(8)]);var inst_14570 = cljs.core._EQ_.call(null,inst_14566,inst_14563);var state_14586__$1 = state_14586;if(inst_14570)
{var statearr_14599_14618 = state_14586__$1;(statearr_14599_14618[(1)] = (8));
} else
{var statearr_14600_14619 = state_14586__$1;(statearr_14600_14619[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14587 === (10)))
{var inst_14578 = (state_14586[(2)]);var state_14586__$1 = state_14586;var statearr_14601_14620 = state_14586__$1;(statearr_14601_14620[(2)] = inst_14578);
(statearr_14601_14620[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14587 === (8)))
{var inst_14563 = (state_14586[(7)]);var tmp14598 = inst_14563;var inst_14563__$1 = tmp14598;var state_14586__$1 = (function (){var statearr_14602 = state_14586;(statearr_14602[(7)] = inst_14563__$1);
return statearr_14602;
})();var statearr_14603_14621 = state_14586__$1;(statearr_14603_14621[(2)] = null);
(statearr_14603_14621[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___14611,out))
;return ((function (switch__6404__auto__,c__6419__auto___14611,out){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_14607 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_14607[(0)] = state_machine__6405__auto__);
(statearr_14607[(1)] = (1));
return statearr_14607;
});
var state_machine__6405__auto____1 = (function (state_14586){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_14586);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e14608){if((e14608 instanceof Object))
{var ex__6408__auto__ = e14608;var statearr_14609_14622 = state_14586;(statearr_14609_14622[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_14586);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e14608;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__14623 = state_14586;
state_14586 = G__14623;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_14586){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_14586);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___14611,out))
})();var state__6421__auto__ = (function (){var statearr_14610 = f__6420__auto__.call(null);(statearr_14610[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___14611);
return statearr_14610;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___14611,out))
);
return out;
});
unique = function(ch,buf_or_n){
switch(arguments.length){
case 1:
return unique__1.call(this,ch);
case 2:
return unique__2.call(this,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
unique.cljs$core$IFn$_invoke$arity$1 = unique__1;
unique.cljs$core$IFn$_invoke$arity$2 = unique__2;
return unique;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.partition = (function() {
var partition = null;
var partition__2 = (function (n,ch){return partition.call(null,n,ch,null);
});
var partition__3 = (function (n,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__6419__auto___14758 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___14758,out){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___14758,out){
return (function (state_14728){var state_val_14729 = (state_14728[(1)]);if((state_val_14729 === (7)))
{var inst_14724 = (state_14728[(2)]);var state_14728__$1 = state_14728;var statearr_14730_14759 = state_14728__$1;(statearr_14730_14759[(2)] = inst_14724);
(statearr_14730_14759[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14729 === (1)))
{var inst_14691 = (new Array(n));var inst_14692 = inst_14691;var inst_14693 = (0);var state_14728__$1 = (function (){var statearr_14731 = state_14728;(statearr_14731[(7)] = inst_14693);
(statearr_14731[(8)] = inst_14692);
return statearr_14731;
})();var statearr_14732_14760 = state_14728__$1;(statearr_14732_14760[(2)] = null);
(statearr_14732_14760[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14729 === (4)))
{var inst_14696 = (state_14728[(9)]);var inst_14696__$1 = (state_14728[(2)]);var inst_14697 = (inst_14696__$1 == null);var inst_14698 = cljs.core.not.call(null,inst_14697);var state_14728__$1 = (function (){var statearr_14733 = state_14728;(statearr_14733[(9)] = inst_14696__$1);
return statearr_14733;
})();if(inst_14698)
{var statearr_14734_14761 = state_14728__$1;(statearr_14734_14761[(1)] = (5));
} else
{var statearr_14735_14762 = state_14728__$1;(statearr_14735_14762[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14729 === (15)))
{var inst_14718 = (state_14728[(2)]);var state_14728__$1 = state_14728;var statearr_14736_14763 = state_14728__$1;(statearr_14736_14763[(2)] = inst_14718);
(statearr_14736_14763[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14729 === (13)))
{var state_14728__$1 = state_14728;var statearr_14737_14764 = state_14728__$1;(statearr_14737_14764[(2)] = null);
(statearr_14737_14764[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14729 === (6)))
{var inst_14693 = (state_14728[(7)]);var inst_14714 = (inst_14693 > (0));var state_14728__$1 = state_14728;if(cljs.core.truth_(inst_14714))
{var statearr_14738_14765 = state_14728__$1;(statearr_14738_14765[(1)] = (12));
} else
{var statearr_14739_14766 = state_14728__$1;(statearr_14739_14766[(1)] = (13));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14729 === (3)))
{var inst_14726 = (state_14728[(2)]);var state_14728__$1 = state_14728;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_14728__$1,inst_14726);
} else
{if((state_val_14729 === (12)))
{var inst_14692 = (state_14728[(8)]);var inst_14716 = cljs.core.vec.call(null,inst_14692);var state_14728__$1 = state_14728;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14728__$1,(15),out,inst_14716);
} else
{if((state_val_14729 === (2)))
{var state_14728__$1 = state_14728;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_14728__$1,(4),ch);
} else
{if((state_val_14729 === (11)))
{var inst_14708 = (state_14728[(2)]);var inst_14709 = (new Array(n));var inst_14692 = inst_14709;var inst_14693 = (0);var state_14728__$1 = (function (){var statearr_14740 = state_14728;(statearr_14740[(7)] = inst_14693);
(statearr_14740[(8)] = inst_14692);
(statearr_14740[(10)] = inst_14708);
return statearr_14740;
})();var statearr_14741_14767 = state_14728__$1;(statearr_14741_14767[(2)] = null);
(statearr_14741_14767[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14729 === (9)))
{var inst_14692 = (state_14728[(8)]);var inst_14706 = cljs.core.vec.call(null,inst_14692);var state_14728__$1 = state_14728;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14728__$1,(11),out,inst_14706);
} else
{if((state_val_14729 === (5)))
{var inst_14696 = (state_14728[(9)]);var inst_14701 = (state_14728[(11)]);var inst_14693 = (state_14728[(7)]);var inst_14692 = (state_14728[(8)]);var inst_14700 = (inst_14692[inst_14693] = inst_14696);var inst_14701__$1 = (inst_14693 + (1));var inst_14702 = (inst_14701__$1 < n);var state_14728__$1 = (function (){var statearr_14742 = state_14728;(statearr_14742[(11)] = inst_14701__$1);
(statearr_14742[(12)] = inst_14700);
return statearr_14742;
})();if(cljs.core.truth_(inst_14702))
{var statearr_14743_14768 = state_14728__$1;(statearr_14743_14768[(1)] = (8));
} else
{var statearr_14744_14769 = state_14728__$1;(statearr_14744_14769[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14729 === (14)))
{var inst_14721 = (state_14728[(2)]);var inst_14722 = cljs.core.async.close_BANG_.call(null,out);var state_14728__$1 = (function (){var statearr_14746 = state_14728;(statearr_14746[(13)] = inst_14721);
return statearr_14746;
})();var statearr_14747_14770 = state_14728__$1;(statearr_14747_14770[(2)] = inst_14722);
(statearr_14747_14770[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14729 === (10)))
{var inst_14712 = (state_14728[(2)]);var state_14728__$1 = state_14728;var statearr_14748_14771 = state_14728__$1;(statearr_14748_14771[(2)] = inst_14712);
(statearr_14748_14771[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14729 === (8)))
{var inst_14701 = (state_14728[(11)]);var inst_14692 = (state_14728[(8)]);var tmp14745 = inst_14692;var inst_14692__$1 = tmp14745;var inst_14693 = inst_14701;var state_14728__$1 = (function (){var statearr_14749 = state_14728;(statearr_14749[(7)] = inst_14693);
(statearr_14749[(8)] = inst_14692__$1);
return statearr_14749;
})();var statearr_14750_14772 = state_14728__$1;(statearr_14750_14772[(2)] = null);
(statearr_14750_14772[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___14758,out))
;return ((function (switch__6404__auto__,c__6419__auto___14758,out){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_14754 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_14754[(0)] = state_machine__6405__auto__);
(statearr_14754[(1)] = (1));
return statearr_14754;
});
var state_machine__6405__auto____1 = (function (state_14728){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_14728);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e14755){if((e14755 instanceof Object))
{var ex__6408__auto__ = e14755;var statearr_14756_14773 = state_14728;(statearr_14756_14773[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_14728);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e14755;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__14774 = state_14728;
state_14728 = G__14774;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_14728){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_14728);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___14758,out))
})();var state__6421__auto__ = (function (){var statearr_14757 = f__6420__auto__.call(null);(statearr_14757[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___14758);
return statearr_14757;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___14758,out))
);
return out;
});
partition = function(n,ch,buf_or_n){
switch(arguments.length){
case 2:
return partition__2.call(this,n,ch);
case 3:
return partition__3.call(this,n,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
partition.cljs$core$IFn$_invoke$arity$2 = partition__2;
partition.cljs$core$IFn$_invoke$arity$3 = partition__3;
return partition;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.partition_by = (function() {
var partition_by = null;
var partition_by__2 = (function (f,ch){return partition_by.call(null,f,ch,null);
});
var partition_by__3 = (function (f,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__6419__auto___14917 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___14917,out){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___14917,out){
return (function (state_14887){var state_val_14888 = (state_14887[(1)]);if((state_val_14888 === (7)))
{var inst_14883 = (state_14887[(2)]);var state_14887__$1 = state_14887;var statearr_14889_14918 = state_14887__$1;(statearr_14889_14918[(2)] = inst_14883);
(statearr_14889_14918[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14888 === (1)))
{var inst_14846 = [];var inst_14847 = inst_14846;var inst_14848 = new cljs.core.Keyword("cljs.core.async","nothing","cljs.core.async/nothing",-69252123);var state_14887__$1 = (function (){var statearr_14890 = state_14887;(statearr_14890[(7)] = inst_14847);
(statearr_14890[(8)] = inst_14848);
return statearr_14890;
})();var statearr_14891_14919 = state_14887__$1;(statearr_14891_14919[(2)] = null);
(statearr_14891_14919[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14888 === (4)))
{var inst_14851 = (state_14887[(9)]);var inst_14851__$1 = (state_14887[(2)]);var inst_14852 = (inst_14851__$1 == null);var inst_14853 = cljs.core.not.call(null,inst_14852);var state_14887__$1 = (function (){var statearr_14892 = state_14887;(statearr_14892[(9)] = inst_14851__$1);
return statearr_14892;
})();if(inst_14853)
{var statearr_14893_14920 = state_14887__$1;(statearr_14893_14920[(1)] = (5));
} else
{var statearr_14894_14921 = state_14887__$1;(statearr_14894_14921[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14888 === (15)))
{var inst_14877 = (state_14887[(2)]);var state_14887__$1 = state_14887;var statearr_14895_14922 = state_14887__$1;(statearr_14895_14922[(2)] = inst_14877);
(statearr_14895_14922[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14888 === (13)))
{var state_14887__$1 = state_14887;var statearr_14896_14923 = state_14887__$1;(statearr_14896_14923[(2)] = null);
(statearr_14896_14923[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14888 === (6)))
{var inst_14847 = (state_14887[(7)]);var inst_14872 = inst_14847.length;var inst_14873 = (inst_14872 > (0));var state_14887__$1 = state_14887;if(cljs.core.truth_(inst_14873))
{var statearr_14897_14924 = state_14887__$1;(statearr_14897_14924[(1)] = (12));
} else
{var statearr_14898_14925 = state_14887__$1;(statearr_14898_14925[(1)] = (13));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14888 === (3)))
{var inst_14885 = (state_14887[(2)]);var state_14887__$1 = state_14887;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_14887__$1,inst_14885);
} else
{if((state_val_14888 === (12)))
{var inst_14847 = (state_14887[(7)]);var inst_14875 = cljs.core.vec.call(null,inst_14847);var state_14887__$1 = state_14887;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14887__$1,(15),out,inst_14875);
} else
{if((state_val_14888 === (2)))
{var state_14887__$1 = state_14887;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_14887__$1,(4),ch);
} else
{if((state_val_14888 === (11)))
{var inst_14855 = (state_14887[(10)]);var inst_14851 = (state_14887[(9)]);var inst_14865 = (state_14887[(2)]);var inst_14866 = [];var inst_14867 = inst_14866.push(inst_14851);var inst_14847 = inst_14866;var inst_14848 = inst_14855;var state_14887__$1 = (function (){var statearr_14899 = state_14887;(statearr_14899[(7)] = inst_14847);
(statearr_14899[(11)] = inst_14867);
(statearr_14899[(12)] = inst_14865);
(statearr_14899[(8)] = inst_14848);
return statearr_14899;
})();var statearr_14900_14926 = state_14887__$1;(statearr_14900_14926[(2)] = null);
(statearr_14900_14926[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14888 === (9)))
{var inst_14847 = (state_14887[(7)]);var inst_14863 = cljs.core.vec.call(null,inst_14847);var state_14887__$1 = state_14887;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14887__$1,(11),out,inst_14863);
} else
{if((state_val_14888 === (5)))
{var inst_14855 = (state_14887[(10)]);var inst_14851 = (state_14887[(9)]);var inst_14848 = (state_14887[(8)]);var inst_14855__$1 = f.call(null,inst_14851);var inst_14856 = cljs.core._EQ_.call(null,inst_14855__$1,inst_14848);var inst_14857 = cljs.core.keyword_identical_QMARK_.call(null,inst_14848,new cljs.core.Keyword("cljs.core.async","nothing","cljs.core.async/nothing",-69252123));var inst_14858 = (inst_14856) || (inst_14857);var state_14887__$1 = (function (){var statearr_14901 = state_14887;(statearr_14901[(10)] = inst_14855__$1);
return statearr_14901;
})();if(cljs.core.truth_(inst_14858))
{var statearr_14902_14927 = state_14887__$1;(statearr_14902_14927[(1)] = (8));
} else
{var statearr_14903_14928 = state_14887__$1;(statearr_14903_14928[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14888 === (14)))
{var inst_14880 = (state_14887[(2)]);var inst_14881 = cljs.core.async.close_BANG_.call(null,out);var state_14887__$1 = (function (){var statearr_14905 = state_14887;(statearr_14905[(13)] = inst_14880);
return statearr_14905;
})();var statearr_14906_14929 = state_14887__$1;(statearr_14906_14929[(2)] = inst_14881);
(statearr_14906_14929[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14888 === (10)))
{var inst_14870 = (state_14887[(2)]);var state_14887__$1 = state_14887;var statearr_14907_14930 = state_14887__$1;(statearr_14907_14930[(2)] = inst_14870);
(statearr_14907_14930[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14888 === (8)))
{var inst_14847 = (state_14887[(7)]);var inst_14855 = (state_14887[(10)]);var inst_14851 = (state_14887[(9)]);var inst_14860 = inst_14847.push(inst_14851);var tmp14904 = inst_14847;var inst_14847__$1 = tmp14904;var inst_14848 = inst_14855;var state_14887__$1 = (function (){var statearr_14908 = state_14887;(statearr_14908[(7)] = inst_14847__$1);
(statearr_14908[(14)] = inst_14860);
(statearr_14908[(8)] = inst_14848);
return statearr_14908;
})();var statearr_14909_14931 = state_14887__$1;(statearr_14909_14931[(2)] = null);
(statearr_14909_14931[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__6419__auto___14917,out))
;return ((function (switch__6404__auto__,c__6419__auto___14917,out){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_14913 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_14913[(0)] = state_machine__6405__auto__);
(statearr_14913[(1)] = (1));
return statearr_14913;
});
var state_machine__6405__auto____1 = (function (state_14887){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_14887);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e14914){if((e14914 instanceof Object))
{var ex__6408__auto__ = e14914;var statearr_14915_14932 = state_14887;(statearr_14915_14932[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_14887);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e14914;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__14933 = state_14887;
state_14887 = G__14933;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_14887){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_14887);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___14917,out))
})();var state__6421__auto__ = (function (){var statearr_14916 = f__6420__auto__.call(null);(statearr_14916[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___14917);
return statearr_14916;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___14917,out))
);
return out;
});
partition_by = function(f,ch,buf_or_n){
switch(arguments.length){
case 2:
return partition_by__2.call(this,f,ch);
case 3:
return partition_by__3.call(this,f,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
partition_by.cljs$core$IFn$_invoke$arity$2 = partition_by__2;
partition_by.cljs$core$IFn$_invoke$arity$3 = partition_by__3;
return partition_by;
})()
;

//# sourceMappingURL=async.js.map