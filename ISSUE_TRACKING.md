# ExtraDelight Forge 1.20.1 - Issue Tracking

> 最后更新: 2026-05-19 (已同步远程仓库)

---

## 远程仓库同步 (2026-05-19)

从 `https://github.com/TansuoTro/extradelight-forge-1.20.1` 拉取了以下更新:

### 已合并的远程提交
| 提交 | 说明 |
|------|------|
| `7ebb2925` | **同步资源** - 修复worldgen和模型问题，新增11,675个文件 |
| `a5f42a32` | **Create mod可选化** - FluidIngredient → FluidIngredientCompat |

### 远程修复的问题
- ✅ 叶子不能正常显示 → 新增水果树叶生长阶段模型 (苹果/葡萄柚/柠檬/青柠/橙子/榛子)
- ✅ fruit_bowl紫黑块 → 已生成含50种样式的blockstate
- ✅ 新增Summer Citrus内容 (榨汁机、水果碗、野餐篮等)
- ✅ 新增法语本地化 (fr_ca.json)
- ✅ 修复worldgen (世界生成) 问题
- ✅ 移除Create mod硬依赖

### 远程引入的问题 (已修复)
- ⚠️ 删除了所有手动blockstate文件 (92个)，但生成资源中缺少:
  - **工作站方块**: oven, melting_pot, funnel, chiller → **已从git恢复**
  - **厨房家具**: 13种木材的counter_cabinet/sink/half_cabinet → **已从git恢复**
  - **其他**: breadcrumbs, mint_crop, raw_cinnamon_block → **已从git恢复**

---

## 1. 交互修复 (Block Interaction Fixes)

### 1.1 搅拌碗 (MixingBowlBlock) 
- **状态**: ✅ 已修复
- **问题**: `use()` 空手时返回 `InteractionResult.SUCCESS` 阻止了服务端包发送；`useItemOn()` 客户端返回 `SUCCESS` 导致无法触发服务端逻辑
- **修复**: `use()` 改为返回 `InteractionResult.CONSUME`；`useItemOn()` 改为客户端返回 `CONSUME`
- **文件**: `MixingBowlBlock.java`

### 1.2 研磨碗 (MortarBlock)
- **状态**: ✅ 已修复
- **问题**: `useItemOn()` 缺少客户端检查，与服务端交互调度错误
- **修复**: 添加 `pLevel.isClientSide` 检查并返回 `CONSUME`
- **文件**: `MortarBlock.java`

### 1.3 烤炉 (OvenBlock)
- **状态**: ✅ 已修复
- **问题**: `useWithoutItem()` 始终返回 `InteractionResult.SUCCESS`，客户端直接消费交互
- **修复**: 改为返回 `InteractionResult.CONSUME`
- **文件**: `OvenBlock.java`

### 1.4 晾干架 (DryingRackBlock)
- **状态**: ✅ 已修复
- **问题**: 空手时客户端返回 `PASS`；潜行时客户端返回 `CONSUME` 阻止服务端触发
- **修复**: 空手时返回 `CONSUME`；潜行时客户端改为返回 `PASS`
- **文件**: `DryingRackBlock.java`

### 1.5 砧板 (CuttingBoard)
- **状态**: ⏳ 需检查
- **依赖**: Farmer's Delight 原版砧板 + 自定义配方
- **备注**: 砧板交互由 FD 控制，如有问题可能是配方 tag 缺失

### 1.6 食物方块分餐 (RecipeFeastBlock)
- **状态**: ✅ 已修复
- **问题**: `useItemOn()` 客户端尝试运行 `takeServing()`（配方查找在客户端会失败）
- **修复**: 客户端始终返回 `CONSUME`，让服务端处理分餐逻辑
- **文件**: `RecipeFeastBlock.java`

---

## 2. 纹理/模型修复 (Texture/Model Fixes)

### 2.1 烤炉 (Oven)
- **状态**: ⚠️ 资产完整（blockstate + model + texture 都存在），需测试确认
- **文件位置**: 
  - blockstate: `src/main/resources/assets/extradelight/blockstates/oven.json` ✅
  - model: `src/main/resources/assets/extradelight/models/block/oven.json` ✅
  - texture: `src/main/resources/assets/extradelight/textures/block/oven.png` (6252 bytes) ✅
- **纹理引用**: `extradelight:block/oven`, `minecraft:block/bricks`, `minecraft:block/light_gray_terracotta`

### 2.2 熔锅 (Melting Pot)
- **状态**: ⚠️ 资产完整，纹理依赖 Farmer's Delight
- **依赖**: `farmersdelight:block/cooking_pot_*` 纹理系列（由 FD mod 提供）
- **文件位置**:
  - blockstate: `src/main/resources/assets/extradelight/blockstates/melting_pot.json` ✅
  - model: `src/main/resources/assets/extradelight/models/block/melting_pot.json` ✅

### 2.3 冷凝机 (Condenser)
- **状态**: ❌ 此方块在代码中不存在
- **说明**: 没有任何注册、blockstate、模型或纹理文件。需额外开发实现

### 2.4 漏斗 (Funnel)
- **状态**: ⚠️ 资产完整，需测试确认
- **文件位置**:
  - blockstate: `src/main/resources/assets/extradelight/blockstates/funnel.json` ✅
  - model: `src/main/resources/assets/extradelight/models/block/funnel.json` ✅
  - textures: `funnel_outside.png`, `funnel_top.png`, `funnel_inside.png` ✅

### 2.5 烤肉饼 (Burger Patty)
- **状态**: ✅ 无需修复（这是 FD 的 `beef_patty` 物品，不是方块）

### 2.6 烤肉大餐 (Pot Roast Feast → `potroast_block`)
- **状态**: ✅ blockstate 已存在于生成资源中
- **文件**: `src/generated/resources/assets/extradelight/blockstates/potroast_block.json` ✅
- **模型**: `src/generated/resources/assets/extradelight/models/block/potroast_block_stage*.json` ✅
- **纹理**: `src/main/resources/assets/extradelight/textures/block/potroast.png` (1034 bytes) ✅

### 2.7 烤羊排 (Rack Lamb → `rack_lamb_block`)
- **状态**: ✅ blockstate 已存在于生成资源中
- **文件**: `src/generated/resources/assets/extradelight/blockstates/rack_lamb_block.json` ✅
- **纹理**: `src/main/resources/assets/extradelight/textures/block/rack_ribs.png` (5850 bytes) ✅

### 2.8 惠灵顿牛排 (Beef Wellington → `beef_wellington_block`)
- **状态**: ✅ blockstate 已存在于生成资源中
- **文件**: `src/generated/resources/assets/extradelight/blockstates/beef_wellington_block.json` ✅
- **纹理**: `src/main/resources/assets/extradelight/textures/block/beefwellington.png` (5917 bytes) ✅

### 2.9 干玉米栅栏纹理 (Dried Corn Fence)
- **状态**: ✅ 已修复
- **问题**: 模型文件 `dried_corn_fence.json` 中纹理 `#1` 引用 `"block/texture"`（不存在）
- **修复**: 改为 `"extradelight:block/crops/corn/dried_corn_1"`
- **文件**: `dried_corn_fence.json`

---

## 3. 栅栏连接 (Fence Connections)

### 3.1 木栅栏 (Wood Fences)
- **状态**: ⚠️ 资产完整，需测试确认
- **说明**: 
  - cinnamon_fence, fruit_fence 等注册为 `FenceBlock`
  - blockstate 使用 `multipart` 格式（`cinnamon_fence.json` 已验证）✅
  - fence_post 和 fence_side 模型在 `src/generated/resources/` ✅
  - 纹理: `extradelight:block/cinnamon_planks` (5560 bytes) ✅

### 3.2 巧克力栅栏 (Chocolate Fences)
- **状态**: ⚠️ 同木栅栏，资产完整

### 3.3 干玉米栅栏 (Dried Corn Fence)
- **状态**: ✅ 纹理已修复

---

## 4. EMI 配方支持
- **状态**: ❌ 暂不处理

---

## 5. 生成资源 (Generated Resources)
- **位置**: `src/generated/resources/`
- **内容**: 包含 blockstates, models/item, models/block, data recipes/tags
- **状态**: ✅ `build.gradle` 第138行已正确配置
- **配置**: `sourceSets.main.resources { srcDir 'src/generated/resources' }`
- **fence blockstate**: 使用 `multipart` 格式，post/side 模型齐全 ✅
- **feast blocks**: `potroast_block.json`, `rack_lamb_block.json`, `beef_wellington_block.json` 都存在 ✅

---

## 修复清单总览

| # | 问题 | 文件 | 状态 |
|---|------|------|------|
| 1 | 搅拌碗不能交互 | `MixingBowlBlock.java` | ✅ |
| 2 | 研磨碗不能交互 | `MortarBlock.java` | ✅ |
| 3 | 烤炉不能交互 | `OvenBlock.java` | ✅ |
| 4 | 晾干架不能交互 | `DryingRackBlock.java` | ✅ |
| 5 | 砧板交互有错 | FD CuttingBoard | ⏳ |
| 6 | 食物方块不能分餐 | `RecipeFeastBlock.java` | ✅ |
| 7-12 | 紫黑纹理块 | 见2.1-2.8 | ⚠️（资产齐全需测试） |
| 13 | 栅栏不能连接 | blockstate | ⚠️（multipart格式正确需测试） |
| 14 | EMI配方 | - | ❌ |
| 15 | 干玉米栅栏纹理 | `dried_corn_fence.json` | ✅ |
