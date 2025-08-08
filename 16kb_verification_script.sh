#!/bin/bash

# Script to verify 16KB page size compatibility
# Run this after building your APK

APK_NAME="$1"

if [ -z "$APK_NAME" ]; then
    echo "Usage: $0 <APK_NAME.apk>"
    exit 1
fi

echo "Checking 16KB page size compatibility for $APK_NAME"
echo "=========================================="

# Check if APK exists
if [ ! -f "$APK_NAME" ]; then
    echo "Error: APK file $APK_NAME not found!"
    exit 1
fi

# Create temporary directory
TEMP_DIR="/tmp/apk_check_$$"
mkdir -p "$TEMP_DIR"

# Extract APK
echo "Extracting APK..."
unzip -q "$APK_NAME" -d "$TEMP_DIR"

# Check for native libraries
echo "Checking for native libraries..."
if [ -d "$TEMP_DIR/lib" ]; then
    echo "Found native libraries:"
    find "$TEMP_DIR/lib" -name "*.so" | while read -r so_file; do
        echo "  - $(basename "$so_file")"
        # Check ELF alignment (requires Android SDK tools)
        if command -v llvm-objdump >/dev/null 2>&1; then
            alignment=$(llvm-objdump -p "$so_file" | grep "LOAD" | head -1 | awk '{print $NF}')
            if [[ "$alignment" =~ 2\*\*14 ]]; then
                echo "    ✓ 16KB aligned"
            else
                echo "    ✗ NOT 16KB aligned ($alignment)"
            fi
        fi
    done
else
    echo "✓ No native libraries found - app should be compatible"
fi

# Check zipalign
if command -v zipalign >/dev/null 2>&1; then
    echo "Checking APK alignment..."
    if zipalign -c -v 4 "$APK_NAME" >/dev/null 2>&1; then
        echo "✓ APK is properly aligned"
    else
        echo "✗ APK alignment issues detected"
    fi
fi

# Cleanup
rm -rf "$TEMP_DIR"

echo "=========================================="
echo "16KB compatibility check complete!"