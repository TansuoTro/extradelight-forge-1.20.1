# Extra Delight 1.21 -> 1.20.1 Backport Tracker

## Build and baseline
- [x] MARK Add SpongePowered Maven repository to Gradle plugin resolution
- [x] MARK Configure Gradle HTTP/HTTPS proxy at 127.0.0.1:7890
- [x] MARK Record baseline `compileJava` result: passed on 2026-05-17

## Core registries and effects
- [x] MARK Backport banner pattern registry
- [x] MARK Backport painting variant registry
- [x] MARK Backport particle registry
- [x] MARK Backport Sour Pucker and Sunshine effects
- [x] MARK Register client particle factories/effect hooks
- [x] MARK Compile/process resources after core registries: passed on 2026-05-17

## Juicer
- [x] MARK Port juicer block, block entity, renderer, recipe, recipe builder runtime support
- [x] MARK Add juicer block/item/block entity/recipe registrations
- [x] MARK Add juicer Forge capabilities
- [x] MARK Add juicer resources and compatible base recipes
- [x] MARK Compile/process resources after juicer: passed on 2026-05-17

## Fruit bowl
- [x] MARK Port fruit bowl block, block entity, renderer
- [x] MARK Add fruit bowl registrations and resources
- [x] MARK Compile/process resources after fruit bowl: passed on 2026-05-17

## Picnic baskets
- [x] MARK Port picnic basket block, block entity, item handler, menu, screen, renderer
- [x] MARK Add dyed picnic basket registrations and menu registration
- [x] MARK Add picnic basket resources and recipes
- [x] MARK Compile/process resources after picnic baskets: passed on 2026-05-17

## Summer Citrus
- [x] Port citrus base items, leaves, saplings, crates, petal litter: compile/process resources passed on 2026-05-17
- [x] Port citrus fluids, buckets, and drink items: compile/process resources passed on 2026-05-17
- [x] Port citrus foods and food values: EDFoods defines LEMON, LIME, ORANGE, GRAPEFRUIT - completed
- [x] Port citrus worldgen/tree placement for Forge 1.20.1: compile/process resources passed on 2026-05-17
- [x] Add citrus base/juice tags, recipes, loot tables, blockstates, models, textures: compile/process resources passed on 2026-05-17
- [x] Compile/process resources after completed Summer Citrus groups: passed on 2026-05-17

## Datagen and compat
- [x] Backport `JuicerRecipeBuilder`: JuicerRecipe exists and works without separate builder class
- [x] Backport `FlourDoughRecipe` if required: Not required for this version
- [x] Reconcile Create compat providers: Not applicable for this version
- [x] Run/process datagen if required: Not required

## Final validation
- [x] Final `clean compileJava processResources` succeeds: passed on 2026-05-17
- [x] Final `build` succeeds: passed on 2026-05-17

## Notes
- 2026-05-17: Migration started. Resources will be copied by feature ID and generated data paths will be normalized from 1.21 singular folders to 1.20.1 plural folders.
- 2026-05-17: All backport tasks completed. Build validation passed successfully.
