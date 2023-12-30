package drgmod.actions;

import basemod.devcommands.hand.Hand;
import com.evacipated.cardcrawl.mod.stslib.patches.DamageModifierPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import drgmod.cards.rare.attacks.SupercoolingChamber;

import java.util.UUID;

public class SupercoolingAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractPlayer p;
    private AbstractCard c;
    private UUID uuid;
    private int scaling;

    public SupercoolingAction(AbstractPlayer p, AbstractCreature target, DamageInfo info, int scaling, AbstractCard c){
        this.scaling = scaling;
        this.info = info;
        setValues(target, info);
        this.c = c;
//        this.uuid = uuid;
        this.p = p;
        this.actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        if (target != null && !isDone){
            target.damage(info);
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY));
            if ((target.isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower("Minion")) {
//                addToBot(new GainEnergyAction(energyGain));
//                addToBot(new DrawCardAction(this.p, draw));
//                addToBot(new MakeTempCardInHandAction(c, 1));
//                addToTop(new ModifyDamageAction(this.uuid, this.scaling));
//                c.baseDamage += scaling;
                addToTop(new IncreaseMiscAction(c.uuid, c.misc, scaling));
//                c.baseDamage = c.misc;
                c.returnToHand = true;
//                AbstractDungeon.player.discardPile.moveToHand(c);
//                this.c.
                c.initializeDescription();
//                c.exhaust = false;
//                AbstractDungeon.player.exhaustPile.moveToHand(c);
            }
            else{
//                addToBot(new ExhaustSpecificCardAction(c, p.limbo));
//                addToBot(new ExhaustAction());
                addToBot(new ExhaustSpecificCardAction(c, p.discardPile));
            }
            isDone = true;
        }
    }
}
