#!/bin/bash
# Calculates the next semantic version based on conventional commits
# Outputs: version, tag, bump_type

set -e

# Get the latest tag (or default to v0.0.0 if none exists)
LATEST_TAG=$(git describe --tags --abbrev=0 2>/dev/null || echo "v0.0.0")
echo "Latest tag: $LATEST_TAG" >&2

# Strip 'v' prefix if present
LATEST_VERSION=${LATEST_TAG#v}

# Parse semver components
IFS='.' read -r MAJOR MINOR PATCH <<< "$LATEST_VERSION"
MAJOR=${MAJOR:-0}
MINOR=${MINOR:-0}
PATCH=${PATCH:-0}

# Analyze commits since last tag to determine version bump
COMMITS=$(git log ${LATEST_TAG}..HEAD --oneline 2>/dev/null || git log --oneline)

BUMP_TYPE="patch"

# Check for breaking changes (major bump)
if echo "$COMMITS" | grep -qiE "^[a-f0-9]+ [a-z]+(\(.+\))?!:|BREAKING CHANGE"; then
  BUMP_TYPE="major"
# Check for features (minor bump)
elif echo "$COMMITS" | grep -qiE "^[a-f0-9]+ feat(\(.+\))?:"; then
  BUMP_TYPE="minor"
fi

# Calculate new version
case $BUMP_TYPE in
  major)
    MAJOR=$((MAJOR + 1))
    MINOR=0
    PATCH=0
    ;;
  minor)
    MINOR=$((MINOR + 1))
    PATCH=0
    ;;
  patch)
    PATCH=$((PATCH + 1))
    ;;
esac

NEW_VERSION="${MAJOR}.${MINOR}.${PATCH}"
echo "Next version: v$NEW_VERSION (bump type: $BUMP_TYPE)" >&2

# Output for GitHub Actions
if [ -n "$GITHUB_OUTPUT" ]; then
  echo "version=$NEW_VERSION" >> $GITHUB_OUTPUT
  echo "tag=v$NEW_VERSION" >> $GITHUB_OUTPUT
  echo "bump_type=$BUMP_TYPE" >> $GITHUB_OUTPUT
fi

# Also output to stdout for other uses
echo "$NEW_VERSION"
