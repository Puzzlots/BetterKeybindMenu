import click
import json
import os

key_to_replace = {
    "Forward": "base:forward",
    "Backward": "base:backward",
    "Left": "base:left",
    "Right": "base:right",
    "Jump": "base:jump",
    "Crouch": "base:crouch",
    "Sprint": "base:sprint",
    "Prone": "base:prone",
    "Inventory": "base:openInventory",
    "Drop_Item": "base:dropItem",
    "Cycle_Item_Type": "base:swapGroupItem",
    "Hide_UI": "base:hideUI",
    "Screenshot": "base:screenshot",
    "Debug_Info": "base:debugInfo",
    "Change_Perspective": "base:changePerspective",
    "Reload_Shaders": "base:reloadShaders",
    "Fullscreen": "base:fullscreen",
    "Attack_Break": "base:attackBreak",
    "Pick_Block": "base:pickBlock",
    "Use_Place": "base:usePlace",
    "Open_Chat": "base:chat",
    "Push_To_Talk": "base:voice"
}

def update_dir(dir: str):
    game_json_path = os.path.join(dir, "game.json")
    keybinds_json_path = os.path.join(dir, "keybinds.json")
    new_entries = {}

    with open(game_json_path, 'r', encoding='utf-8') as file:
        game_json = json.load(file)

    remaining = {}
    for key, value in game_json.items():
        if key in key_to_replace:
            new_entries[key_to_replace[key]] = value
        else:
            remaining[key] = value

    with open(game_json_path, 'w', encoding='utf-8') as file:
        json.dump(remaining, file, indent=2, ensure_ascii=False)

    if os.path.exists(keybinds_json_path):
        with open(keybinds_json_path, 'r', encoding='utf-8') as file:
            json_file = json.load(file)

        json_file.update(new_entries)
    else:
        json_file = new_entries

    with open(keybinds_json_path, 'w', encoding='utf-8') as file:
        json.dump(json_file, file, indent=2, ensure_ascii=False)

@click.command()
@click.option('--lang', type=str, default=None, help='Optional language code to check a specific folder. E.g., en_us. Defaults to None to check all folders.')
def main(lang: str):
    lang_dir = os.path.join("..", "lang")

    folders_to_check = []
    if lang:
        specific_folder_path = os.path.join(lang_dir, lang)
        if os.path.isdir(specific_folder_path):
            folders_to_check.append(lang)
        else:
            print(f"Error: Language folder '{lang}' not found.")
            return
    else:
        folders_to_check = [f for f in os.listdir(lang_dir) if os.path.isdir(os.path.join(lang_dir, f))]

    for lang_folder in folders_to_check:
        lang_folder_path = os.path.join(lang_dir, lang_folder)
        print("Updating " + lang_folder_path)
        update_dir(lang_folder_path)

if __name__ == "__main__":
    # The script is expected to be run from the `scripts` directory
    os.chdir(os.path.dirname(os.path.abspath(__file__)))
    main()