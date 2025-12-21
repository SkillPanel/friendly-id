# Publishing to Maven Central

This document describes how to publish `friendly-id` artifacts to Maven Central using the new Sonatype Central Portal.

## Prerequisites

1. **Account on Central Portal**: https://central.sonatype.com
2. **Namespace verified**: `com.devskiller.friendly-id`
3. **GPG Key**: For signing artifacts
4. **Maven credentials**: Token configured in `~/.m2/settings.xml`

## Configuration

### 1. Maven Settings (`~/.m2/settings.xml`)

Add your Central Portal credentials:

```xml
<servers>
    <server>
        <id>central</id>
        <username>YOUR_USERNAME</username>
        <password>YOUR_TOKEN</password>
    </server>
</servers>
```

### 2. GPG Key Setup

Generate GPG key if you don't have one:

```bash
# Generate key
gpg --gen-key

# List keys
gpg --list-keys

# Export public key to keyserver
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
```

## Publishing Process

### Option 1: Snapshot Release (for testing)

```bash
# Build and deploy snapshot
mvn clean deploy
```

Snapshots will be available at:
```
https://central.sonatype.com/artifact/com.devskiller.friendly-id/friendly-id/1.1.1-SNAPSHOT
```

### Option 2: Release Version

1. **Update version** (remove `-SNAPSHOT` suffix):
```bash
# Update version in pom.xml
mvn versions:set -DnewVersion=1.1.1
mvn versions:commit
```

2. **Build and deploy with release profile**:
```bash
# This will:
# - Create source JARs
# - Create javadoc JARs
# - Sign all artifacts with GPG
# - Deploy to Central Portal
mvn clean deploy -Prelease
```

3. **Verify deployment**:
- Go to https://central.sonatype.com/publishing/deployments
- Check deployment status
- Artifacts will be automatically published to Maven Central (autoPublish=true)

4. **Tag the release**:
```bash
git tag -a 1.1.1 -m "Release version 1.1.1"
git push origin 1.1.1
```

5. **Prepare next development version**:
```bash
mvn versions:set -DnewVersion=1.1.2-SNAPSHOT
mvn versions:commit
git add pom.xml */pom.xml
git commit -m "chore: prepare next development version 1.1.2-SNAPSHOT"
git push
```

## Troubleshooting

### GPG Signing Issues

If you get "gpg: signing failed: Inappropriate ioctl for device":
```bash
export GPG_TTY=$(tty)
```

Or add to `~/.bashrc`:
```bash
export GPG_TTY=$(tty)
```

### Wrong credentials

Make sure `<id>central</id>` in settings.xml matches `<publishingServerId>central</publishingServerId>` in pom.xml.

### Deployment verification

Check deployment status:
```bash
# List recent deployments
curl -u "YOUR_USERNAME:YOUR_TOKEN" \
  https://central.sonatype.com/api/v1/publisher/deployments
```

## Maven Central Sync

After successful deployment:
- Artifacts are **immediately available** on Central Portal
- Sync to Maven Central (repo1.maven.org) takes **10-30 minutes**
- Search index update (search.maven.org) takes **up to 2 hours**

## Verification

After publication, verify artifacts are available:

```bash
# Check on Central Portal
curl https://central.sonatype.com/artifact/com.devskiller.friendly-id/friendly-id/1.1.1

# Check on Maven Central (after sync)
curl https://repo1.maven.org/maven2/com/devskiller/friendly-id/friendly-id/1.1.1/
```

## CI/CD Integration (Future)

For automated releases via GitHub Actions, you'll need to:

1. Add GitHub Secrets:
   - `MAVEN_CENTRAL_USERNAME`
   - `MAVEN_CENTRAL_TOKEN`
   - `GPG_PRIVATE_KEY`
   - `GPG_PASSPHRASE`

2. Create `.github/workflows/release.yml` workflow

3. Use `central-publishing-maven-plugin` in the workflow

## References

- [Central Portal Documentation](https://central.sonatype.org/publish/publish-portal-maven/)
- [Requirements](https://central.sonatype.org/publish/requirements/)
- [Central Publishing Maven Plugin](https://central.sonatype.org/publish/publish-portal-maven/)
