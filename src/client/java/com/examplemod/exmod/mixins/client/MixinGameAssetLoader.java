package com.examplemod.exmod.mixins.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import dev.puzzleshq.mod.api.IModContainer;
import dev.puzzleshq.puzzleloader.loader.util.ModFinder;

import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.util.SaveLocation;
import finalforeach.cosmicreach.util.assets.GameAssetLoader;
import finalforeach.cosmicreach.util.assets.GameAssetLoaderUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

import static com.examplemod.exmod.Constants.clazzAssetList;

@Mixin(GameAssetLoader.class)
public abstract class MixinGameAssetLoader {

    @Shadow
    public static FileHandle loadAsset(Identifier fileName) {
        return null;
    }


    /**
     * would add this to puzzle :)
     */
    @Inject(method =  "<clinit>", at = @At("TAIL"))
    private static void inject(CallbackInfo ci) {
        for (IModContainer modContainer : ModFinder.getModsArray()) {
            FileHandle fileHandle = Gdx.files.internal("assets/" + modContainer.getID() + "/assets.txt");
            if (fileHandle.exists()) {
                clazzAssetList.addAll(List.of(fileHandle.readString().split("\n")));
            }
        }

    }

    /**
     * @author Crab & Lemon (and you lol)
     * @reason Allow custom Java-mod assets.txt loading
     */
    @Overwrite
    public static void forEachAsset(String prefix, String extension, BiConsumer<String, FileHandle> assetConsumer, boolean includeDirectories) {
        ObjectSet<Identifier> allPaths = new ObjectSet<>();
        ObjectSet<Identifier> moddedPaths = new ObjectSet<>();

        for(String assetPath : GameAssetLoaderUtils.defaultAssetList) {
            if (assetPath.startsWith("base/" + prefix.replaceFirst("base:", "")) && assetPath.endsWith(extension)) {
                allPaths.add(Identifier.of("base", assetPath.replaceFirst("base/", "")));
            }
        }

        /// //////////// add this please///////////////////////////

        for (String assetPath : clazzAssetList) {
            String[] splitPath = assetPath.split("/");
            if (assetPath.startsWith(splitPath[0] + "/" + prefix.replaceFirst(splitPath[0] + ":", "")) && assetPath.endsWith(extension)) {
                allPaths.add(Identifier.of(splitPath[0], assetPath.replaceFirst(splitPath[0] + "/", "")));
            }
        }

        /// ///////////////////////////////////////////

        String modAssetFolder = SaveLocation.getSaveFolderLocation() + "/mods/";
        String modAssetRoot = Gdx.files.absolute(modAssetFolder).path().replace("\\", "/");

        for(FileHandle modFolder : Gdx.files.absolute(modAssetRoot).list()) {
            if (modFolder.isDirectory() || includeDirectories) {
                String postPrefix = prefix;
                if (prefix.startsWith(modFolder.file().getName() + ":")) {
                    postPrefix = prefix.substring(modFolder.file().getName().length() + 1);
                }

                String var10000 = String.valueOf(modFolder);
                String modPrefix = var10000 + "/" + postPrefix;
                modPrefix = modPrefix.replace("\\", "/");
                Array<FileHandle> assetList = new Array<>(Gdx.files.absolute(modPrefix).list());

                while(!assetList.isEmpty()) {
                    FileHandle asset = assetList.pop();
                    String assetPath = asset.path().replace("\\", "/").replace(modAssetRoot, "");
                    if (assetPath.startsWith("/")) {
                        assetPath = assetPath.substring(1);
                    }

                    String namespace = modFolder.name();
                    String name = assetPath.replaceFirst(Pattern.quote(namespace + "/"), "");
                    if (name.startsWith(postPrefix) && assetPath.endsWith(extension)) {
                        moddedPaths.add(Identifier.of(namespace, name));
                    }

                    if (asset.isDirectory()) {
                        assetList.addAll(asset.list());
                    }
                }
            }
        }

        allPaths.addAll(moddedPaths);

        for (Identifier path : allPaths) {
            assetConsumer.accept(path.toString(), loadAsset(path));
        }

    }

}
