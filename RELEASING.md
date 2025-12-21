# Releasing Guide

This document describes how to release a new version of FriendlyID to Maven Central.

## Prerequisites

Before releasing, ensure you have:

1. **GitHub Repository Access**: Write access to push tags
2. **GitHub Secrets Configured**: The following secrets must be set in repository settings:
   - `OSSRH_USERNAME` - Sonatype OSSRH username
   - `OSSRH_TOKEN` - Sonatype OSSRH token/password
   - `GPG_PRIVATE_KEY` - GPG private key for artifact signing
   - `GPG_PASSPHRASE` - Passphrase for the GPG key

## Release Process

### 1. Prepare for Release

Ensure your local repository is up to date and all tests pass:

```bash
git checkout master
git pull origin master
mvn clean install
```

### 2. Create Release Tag

Create an annotated tag with the version number (must start with `v`):

```bash
# For release version (e.g., 1.2.0)
git tag -a v1.2.0 -m "Release 1.2.0"

# For release candidate (e.g., 1.2.0-RC1)
git tag -a v1.2.0-RC1 -m "Release 1.2.0-RC1"
```

### 3. Push Tag to GitHub

Push the tag to trigger the automated release:

```bash
git push origin v1.2.0
```

### 4. Monitor Release Process

1. Go to **Actions** tab in GitHub repository
2. Watch the **Release** workflow execution
3. The workflow will:
   - Build all modules with the specified version
   - Run all tests (can be skipped with `-DskipTests`)
   - Sign artifacts with GPG
   - Deploy to Maven Central (OSSRH)
   - Create GitHub Release with artifacts

### 5. Verify Release

After successful deployment:

1. Check [Maven Central Repository](https://repo1.maven.org/maven2/com/devskiller/friendly-id/)
2. Verify the GitHub Release was created with artifacts
3. Test the release in a separate project:

```xml
<dependency>
    <groupId>com.devskiller.friendly-id</groupId>
    <artifactId>friendly-id</artifactId>
    <version>1.2.0</version>
</dependency>
```

## Version Numbering

Follow [Semantic Versioning](https://semver.org/):

- **MAJOR** version (X.0.0): Incompatible API changes
- **MINOR** version (0.X.0): New functionality, backwards-compatible
- **PATCH** version (0.0.X): Backwards-compatible bug fixes

Examples:
- `v1.0.0` - Initial release
- `v1.1.0` - New feature (e.g., OpenFeign integration)
- `v1.1.1` - Bug fix
- `v2.0.0` - Breaking change (e.g., Java version upgrade)

## Snapshot Releases

Snapshot versions are built automatically on every push to `master` branch but are NOT deployed to Maven Central. They use the version defined in the `<revision>` property in the parent POM.

## Manual Release (Emergency)

If automated release fails, you can release manually:

```bash
# Build and deploy to Maven Central
mvn clean deploy -P release -Drevision=1.2.0

# Create GitHub release manually through GitHub UI
```

## Rollback

If a release needs to be rolled back:

1. **Do NOT delete tags from Maven Central** - versions are immutable
2. Delete the GitHub tag and release:
   ```bash
   git tag -d v1.2.0
   git push origin :refs/tags/v1.2.0
   ```
3. Release a new patch version with the fix

## Troubleshooting

### GPG Signing Fails

- Verify `GPG_PRIVATE_KEY` secret is correctly formatted (including `-----BEGIN PGP PRIVATE KEY BLOCK-----`)
- Check `GPG_PASSPHRASE` is correct
- Ensure GPG key hasn't expired

### Maven Central Deployment Fails

- Verify `OSSRH_USERNAME` and `OSSRH_TOKEN` are correct
- Check [OSSRH Status](https://status.maven.org/)
- Review deployment logs in GitHub Actions

### Build Fails

- Check all tests pass locally: `mvn clean install`
- Review GitHub Actions logs for specific error
- Ensure all dependencies are available in Maven Central

## Post-Release Tasks

After successful release:

1. Update `<revision>` in parent `pom.xml` to next SNAPSHOT version
2. Update version in `README.md` examples (if needed)
3. Announce release (GitHub Discussions, Twitter, etc.)
4. Close related GitHub issues/PRs

## CI-Friendly Versioning

This project uses [Maven CI-friendly versioning](https://maven.apache.org/guides/mini/guide-maven-ci-friendly.html). The version is controlled by the `${revision}` property:

- **Default**: Defined in parent `pom.xml` (`1.1.1-SNAPSHOT`)
- **Override**: `mvn -Drevision=X.Y.Z`
- **Release**: GitHub Actions sets version from git tag
