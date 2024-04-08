#!/bin/bash

# 프로그램 실행 명령어
program_command="java Test < Test.in"

# 시간 제한 설정 (예: 10초)
timeout_duration=10

# 프로그램 실행 및 결과 저장 (time 명령어와 함께)
output=$( (time timeout $timeout_duration $program_command) 2>&1 )
exit_code=$?

# 시간 추출
# time 명령어의 출력 형식: real 0m0.001s user 0m0.000s sys 0m0.000s
real_time=$(echo "$output" | grep "real" | awk '{print $2}')

# 메모리 사용량 수집
memory_usage=$(smem -p -r -c "pid rss" | grep $(pgrep -f "$program_command") | awk '{print $6}')  # 수정된 부분

# 결과 추출 (Java 프로그램 출력에 맞게 수정 필요)
# 예시: "Result:" 키워드를 가진 줄에서 결과값 추출
result=$(echo "$output" | grep "Result:" | awk '{print $2}')  # 예시에 맞게 수정 필요

# 실행 결과, 시간, 메모리 사용량, 결과값 출력
echo "Exit Code: $exit_code"
echo "Output:"
echo "$output"
if [ -n "$memory_usage" ]; then
  echo "Memory Usage (RSS): $memory_usage KB"
else
  echo "Memory Usage (RSS): Not available"
fi
echo "Real Time: $real_time"
echo "Result: $result"
