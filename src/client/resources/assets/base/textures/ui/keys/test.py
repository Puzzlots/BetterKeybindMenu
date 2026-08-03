import os
from PIL import Image

INPUT_IMAGE = "gdb-keyboard-2.png"
OUTPUT_DIR = "tiles"
TILE_SIZE = 16

REGIONS = [
    {
        "start_x": 1,
        "start_y": 1,
        "end_x": 14,
        "end_y": 5,
        "keys": [
            [111,131,132,133,134,135,136,137,138,139,140,141,142],
            [68, 8, 9, 10, 11, 12,13,14,15,16,7,69,70],
            [3],
            [4],
            [5],

        ]
    }
]

os.makedirs(OUTPUT_DIR, exist_ok=True)

img = Image.open(INPUT_IMAGE).convert("RGBA")

for region in REGIONS:
    sx, sy = region["start_x"], region["start_y"]
    ex, ey = region["end_x"], region["end_y"]
    keys = region["keys"]

    for row in range(ey - sy):
        for col in range(ex - sx):
            if row >= len(keys) or col >= len(keys[row]):
                continue

            keycode = keys[row][col]

            x = (sx + col) * TILE_SIZE
            y = (sy + row) * TILE_SIZE

            tile = img.crop((x, y, x + TILE_SIZE, y + TILE_SIZE))
            alpha = tile.getchannel("A")
#
            # Skip blank tiles but do not skip keycodes
            if alpha.getextrema() == (0, 0):
                continue  # don't save, but next keycode will still be processed

            tile.save(f"{OUTPUT_DIR}/{keycode}.png")
