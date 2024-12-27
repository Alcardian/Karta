# Define variables
SRC_DIR="src/main/java"
VERSION="1.3.0"
BUILD_DIR="target/Karta_$VERSION"
SCRIPTS_DIR="src/main/resources/scripts"
JAVA_FILE="com/alcardian/karta/ClipboardListener.java"
JAVA_VERSION="8"

# Verify Java version
JAVA_INSTALLED_VERSION=$(java -version 2>&1 | awk -F[\"_] '{print $2}')
echo "Detected Java version: $JAVA_INSTALLED_VERSION"
if [[ "$JAVA_INSTALLED_VERSION" != "1.$JAVA_VERSION"* ]]; then
    echo "Error: Java version 1.$JAVA_VERSION is required but $JAVA_INSTALLED_VERSION is installed."
    exit 1
fi

# Clean up old runs by deleting the target directory
if [ -d "target" ]; then
    echo "Cleaning up old build..."
    rm -rf "target"
fi

# Create the build and scripts directories if they don't exist
echo "Creating build directories..."
mkdir -p "$BUILD_DIR"

# Compile the Java file
echo "Compiling Java file..."
javac -source "$JAVA_VERSION" -target "$JAVA_VERSION" -d "$BUILD_DIR" "$SRC_DIR/$JAVA_FILE"
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

# Copy the run.bat file to the build directory
echo "Copying run.bat to build directory..."
cp "$SCRIPTS_DIR/run.bat" "$BUILD_DIR"
if [ $? -ne 0 ]; then
    echo "Failed to copy run.bat."
    exit 1
fi

# Copy the check_run.bat file to the build directory
echo "Copying run.bat to build directory..."
cp "$SCRIPTS_DIR/check_run.bat" "$BUILD_DIR"
if [ $? -ne 0 ]; then
    echo "Failed to copy check_run.bat."
    exit 1
fi

# Copy the README.md file to the build directory
echo "Copying README.md to build directory..."
cp "README.md" "$BUILD_DIR"
if [ $? -ne 0 ]; then
    echo "Failed to copy README.md."
    exit 1
fi

# Copy the Changelog.md file to the build directory
echo "Copying Changelog.md to build directory..."
cp "Changelog.md" "$BUILD_DIR"
if [ $? -ne 0 ]; then
    echo "Failed to copy Changelog.md."
    exit 1
fi

# Copy the LICENSE file to the build directory
echo "Copying LICENSE to build directory..."
cp "LICENSE" "$BUILD_DIR"
if [ $? -ne 0 ]; then
    echo "Failed to copy LICENSE."
    exit 1
fi

# Print a success message
echo "Build completed. Compiled files are in $BUILD_DIR"

# Debugging
# Wait for the user to press any key before exiting
#read -n 1 -s -r -p "Press any key to terminate..."
#echo # Move to a new line for a clean exit
