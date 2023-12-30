package drgmod.relics;

import basemod.abstracts.CustomSavable;
import basemod.interfaces.StartActSubscriber;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.DualWieldAction;
import com.megacrit.cardcrawl.actions.unique.SummonGremlinAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.sun.javafx.scene.web.Debugger;
import drgmod.actions.AddCardsToHandAction;
import drgmod.actions.BiozoneDuplicationAction;
import drgmod.actions.GainMineralsAction;
import drgmod.actions.MineralSpendAction;
import drgmod.character.MinerCharacter;
import drgmod.powers.*;
import drgmod.util.CustomTags;
import jdk.nashorn.internal.runtime.Debug;

import static drgmod.MinerMain.makeID;

public class DRGBadge extends BaseRelic implements CustomSavable<Integer> {
    private static final String NAME = "DRGBadge"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private int biome = 10;
    private int oldAct = 1;
    public DRGBadge() {
        super(ID, NAME, MinerCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }
    @Override
    public void onEquip() {
        super.onEquip();
        generateBiome();
        modifyDescriptions();
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        super.onEnterRoom(room);
        if (AbstractDungeon.actNum != oldAct){
            flash();
            oldAct = AbstractDungeon.actNum;
            generateBiome();
            modifyDescriptions();
        }
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
//        System.out.println("Biome: " + biome);
        if (biome == 10){
            oldAct = AbstractDungeon.actNum;
            generateBiome();
            modifyDescriptions();
        }
    }

    public void modifyDescriptions(){
        description = DESCRIPTIONS[biome];
        tips.clear();
        tips.add(new PowerTip(this.name, DESCRIPTIONS[biome]));
        initializeTips();
    }

    public void generateBiome() {
        biome = AbstractDungeon.cardRandomRng.random(9);
//        biome = 0;
        this.counter = biome;
        CardCrawlGame.sound.playA(makeID("MOLLY_A3"), MathUtils.random(-0.2F, 0.2F));
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStart();
        if (biome == 4){
            addToBot(new BiozoneDuplicationAction(AbstractDungeon.player, 1));
        }
        else if (biome == 5){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FreezePower(AbstractDungeon.player, 1)));
            for (AbstractMonster m: AbstractDungeon.getMonsters().monsters){
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new FreezePower(m, 1)));
            }
        }
        else if (biome == 8){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BurnPower(AbstractDungeon.player, 1)));
            for (AbstractMonster m: AbstractDungeon.getMonsters().monsters){
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new BurnPower(m, 1)));
            }
        }else if (biome == 9){
            addToBot(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, 2, false));
            addToBot(new DrawCardAction(AbstractDungeon.player, 2));
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        if (biome == 7){
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            if (AbstractDungeon.player.currentHealth > 0)
                AbstractDungeon.player.heal(2);
        }
    }


    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.biome = this.counter;
        modifyDescriptions();
        if (biome == 4){
            AbstractMonster[] gremlins = new AbstractMonster[5];
            float yPos = -30;
            float xPos = -600;
            gremlins[0] = new GremlinTsundere(xPos, yPos);
            gremlins[1] = new GremlinWizard(xPos, yPos);
            gremlins[2] = new GremlinFat(xPos, yPos);
            gremlins[3] = new GremlinWarrior(xPos, yPos);
            gremlins[4] = new GremlinThief(xPos, yPos);
            int k = AbstractDungeon.cardRandomRng.random(4);
            addToBot(new SpawnMonsterAction(gremlins[k], false));
        }
        else if (biome == 6){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 3)));
            for (AbstractMonster m: AbstractDungeon.getMonsters().monsters){
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new ThornsPower(m, 1)));
            }
        }
        else if (biome == 7){
            for (AbstractMonster m: AbstractDungeon.getMonsters().monsters){
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new RegenPower(m, 2)));
            }
        }
    }

    @Override
    public void onSpawnMonster(AbstractMonster monster) {
        super.onSpawnMonster(monster);
        if (biome == 6){
            addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new ThornsPower(monster, 1)));
        }
        else if (biome == 7){
            addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new RegenPower(monster, 2)));
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && AbstractDungeon.player.currentBlock > 0 && biome == 0){
//            if (AbstractDungeon.player.currentBlock > 0){
                AbstractDungeon.player.loseBlock(1);
//            }
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        if (damageAmount > 0 && biome == 0){
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new ShockPower(target, 1)));
        }
    }

//    @Override
//    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
//        super.onAttackToChangeDamage(info, damageAmount);
//         apply shock on damage enemy
//        if (biome == 0 && damageAmount > 0){
//            addToBot(new ApplyPowerAction(info.owner, AbstractDungeon.player, new ShockPower(info.owner, 1)));
//        }
//        return super.onAttackToChangeDamage(info, damageAmount);
//    }

//    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
//        if (biome == 0 && AbstractDungeon.player.currentBlock > damageAmount){
//            damageAmount++;
//            return damageAmount;
//        }
//        return super.onAttackedToChangeDamage(info, damageAmount);
//    }

    //    @Override
//    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
//        super.onAttack(info, damageAmount, target);
//        if (biome == 0){
//            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new ShockPower(target, 1)));
//        }
//    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        super.onMonsterDeath(m);
        if (biome == 2 && (m.isDying || m.currentHealth <= 0) && !m.halfDead && !m.hasPower("Minion")) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, 1, false)));
            for (AbstractMonster k: AbstractDungeon.getMonsters().monsters){
                if (m != k){
                    addToBot(new ApplyPowerAction(k, AbstractDungeon.player, new VulnerablePower(k, 1, false)));
                }
            }
        }
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        if (biome == 3 && AbstractDungeon.player.hasPower(MineralPower.POWER_ID)){
            addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, AbstractDungeon.player.getPower(MineralPower.POWER_ID).amount / 4), AbstractGameAction.AttackEffect.POISON));
        }
    }

//    @Override
//    public int onAttacked(DamageInfo info, int damageAmount) {
//        super.onAttacked(info, damageAmount);
//        if (biome == 0){
//            if (AbstractDungeon.player.currentBlock > 0){
//                if (AbstractDungeon.player.currentBlock >= damageAmount){
//
//                }
//            }
//            if (AbstractDungeon.player.currentBlock > damageAmount){
//                damageAmount++;
//                return damageAmount;
//            }
//
//        }
//        return damageAmount;
//    }

    @Override
    public void onLoseHp(int damageAmount) {
        super.onLoseHp(damageAmount);
        if (biome == 3 && AbstractDungeon.player.hasPower(MineralPower.POWER_ID)){
            addToBot(new MineralSpendAction(damageAmount, AbstractDungeon.player));
        }
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        super.onCardDraw(drawnCard);
        if (drawnCard.hasTag(CustomTags.PICKAXE) && biome == 1){
            drawnCard.setCostForTurn(drawnCard.cost - 1);
        }
    }

    @Override
    public String getUpdatedDescription(){
        return DESCRIPTIONS[10];
    }

    @Override
    public Integer onSave() {
        return this.counter;
    }

    @Override
    public void onLoad(Integer integer) {
        if (integer == null){
            generateBiome();
        }
        this.counter = integer;
        modifyDescriptions();
    }


}
