#include <postgres.h>
#include <fmgr.h>

PG_MODULE_MAGIC;

PG_FUNCTION_INFO_V1(count_stocks);

Datum count_stocks(PG_FUNCTION_ARGS);

Datum
count_stocks(PG_FUNCTION_ARGS)
{
    Oid arg1_type = get_fn_expr_argtype(fcinfo->flinfo, 0);
    if (arg1_type != INT8OID)
        elog(ERROR, "Argument must be of type BIGINT");

    int64 p_id = PG_GETARG_INT64(0);

    int32 stock_count = 0;

    // Your logic here (e.g., execute the SQL query and store the result in stock_count).

    PG_RETURN_INT32(stock_count);
}
