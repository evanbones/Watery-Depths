import os
import shutil
import sys

OLD_IDS = ["example_mod", "mod_template"]
OLD_NAMES = ["Example Mod", "Mod Template"]
OLD_AUTHOR = "Evan"
OLD_PACKAGE = "com.evandev"

def prompt(msg):
    val = input(msg).strip()
    if not val:
        print("Value cannot be empty!")
        sys.exit(1)
    return val

def main():
    print("=== Mod Template Initializer ===")
    new_id = prompt("Enter new Mod ID (e.g., my_awesome_mod): ")
    new_name = prompt("Enter new Mod Name (e.g., My Awesome Mod): ")
    new_author = prompt("Enter new Author Name (e.g., Steve): ")
    new_package = prompt("Enter new Package Base (e.g., net.steve): ")

    old_pkg_path = os.path.join(*OLD_PACKAGE.split("."))
    new_pkg_path = os.path.join(*new_package.split("."))

    print("\nProcessing files...")

    for root, dirs, files in os.walk(".", topdown=False):
        if any(ignored in root for ignored in [".git", "build", "run", ".gradle", "out"]):
            continue

        for file_name in files:
            if file_name == os.path.basename(__file__):
                continue

            filepath = os.path.join(root, file_name)

            try:
                with open(filepath, 'r', encoding='utf-8') as f:
                    content = f.read()

                new_content = content
                for old_id in OLD_IDS:
                    new_content = new_content.replace(old_id, new_id)
                for old_name in OLD_NAMES:
                    new_content = new_content.replace(old_name, new_name)

                new_content = new_content.replace(OLD_PACKAGE, new_package)
                new_content = new_content.replace(OLD_AUTHOR, new_author)

                if new_content != content:
                    with open(filepath, 'w', encoding='utf-8') as f:
                        f.write(new_content)
            except UnicodeDecodeError:
                pass

        for item in dirs + files:
            old_path = os.path.join(root, item)
            new_item = item

            for old_id in OLD_IDS:
                new_item = new_item.replace(old_id, new_id)

            if new_item != item:
                new_path = os.path.join(root, new_item)
                os.rename(old_path, new_path)

    print("Reorganizing Java packages...")

    for root, dirs, files in os.walk(".", topdown=False):
        if old_pkg_path in root:
            new_root = root.replace(old_pkg_path, new_pkg_path)

            os.makedirs(new_root, exist_ok=True)

            for file_name in files:
                old_file = os.path.join(root, file_name)
                new_file = os.path.join(new_root, file_name)
                shutil.move(old_file, new_file)

            try:
                os.rmdir(root)
                parent_dir = os.path.dirname(root)
                if not os.listdir(parent_dir):
                    os.rmdir(parent_dir)
            except OSError:
                pass

    print(f"\nSuccess! Mod '{new_name}' ({new_id}) initialized.")
    print("Deleting setup script...")
    os.remove(__file__)

if __name__ == "__main__":
    main()