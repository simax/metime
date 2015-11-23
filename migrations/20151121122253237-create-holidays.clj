;; migrations/20151121122253237-create-holidays.clj

(defn up []
  ["CREATE TABLE holidays (id integer PRIMARY KEY NOT NULL,
                           [start_date]      date null,
                           [start_type]      varchar(30) null,
                           [end_date]        date null,
                           [employee_id]     integer,
                           [employee_name]   varchar(255) null,
                           [leave_type_id]   integer,
                           [leave_type]      varchar(255) null,
                           [duration]        real,
                           [deduction]       real,
                           [actioned_by_id]  integer,
                           [reason]          varchar(255) null,
                           [declined_reason] varchar(255) null,
                           [status]          varchar(255) null,
                           [unit]            varchar(255) null)"

   "CREATE INDEX [start_date_idx] ON holidays([start_date])"
   "CREATE INDEX [end-date_idx] ON holidays([end_date])"
   "CREATE INDEX [employee_id_idx] ON holidays([employee_id])"
   ])

(defn down []
  ["DROP TABLE holidays"])
