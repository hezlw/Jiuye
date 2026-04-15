#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
OUT_DIR="$ROOT_DIR/dist"
OUT_FILE="$OUT_DIR/bsjob-code.zip"

mkdir -p "$OUT_DIR"
cd "$ROOT_DIR"

zip -r "$OUT_FILE" backend frontend README.md .gitignore -x "*/target/*" "*.DS_Store" > /dev/null

echo "打包完成: $OUT_FILE"
