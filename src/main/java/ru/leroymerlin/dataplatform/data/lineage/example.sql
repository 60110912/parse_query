SELECT *
FROM jsonb_path_query(
$$ { "bound" :[
    {
      "operation": "merge_partitions",
      "granularity": "1 month",
      "lower_bound": "10 month",
      "upper_bound": "-3 month",
      "table_space": "pg_default"
    },
    {
      "operation": "create_partitions",
      "granularity": "1 month",
      "lower_bound": "10 month",
      "upper_bound": "-3 month",
      "table_space": "pg_default"
    }
  ]
}
$$::jsonb,'$.**.operation')