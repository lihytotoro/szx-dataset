export CUDA_VISIBLE_DEVICES="0,1,2,3"
accelerate launch --main_process_port 29502 llama_sft.py \
    --model_name_or_path /data/lihy/codellama/CodeLlama-7b-Instruct-hf \
    --data_path /data/lihy/datasets/java-juliet/src/parsed_dataset/parquet \
    --train_file finetuning_data_maxlen_2048_repairllama-cwe_2_train.parquet \
    --eval_file finetuning_data_maxlen_2048_repairllama-cwe_2_test.parquet \
    --is_lora True \
    --from_lora True \
    --lora_path /data/lihy/training_output/repairllama/finetuned-models_maxlen=1024_epoch=2_newdata_wo_comment_wo_initial_prompt \
    --model_max_length 2048 \
    --cache_path /data/lihy/training_output/repairllama-cwe/cache \
    --do_train \
    --do_eval False \
    --fp16 True \
    --output_dir /data/lihy/training_output/repairllama-cwe/re-finetuned-models_maxlen=2048_epoch=2_juliet-files=2 \
    --num_train_epochs 2 \
    --per_device_train_batch_size 2 \
    --per_device_eval_batch_size 1 \
    --gradient_accumulation_steps 1 \
    --evaluation_strategy "no" \
    --eval_steps 10 \
    --save_steps 150 \
    --learning_rate 5e-4 \
    --lr_scheduler_type "cosine" \
    --logging_steps 10 \
    --ddp_find_unused_parameters False \
