package drgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import drgmod.powers.BurnPower;
import drgmod.powers.MineralPower;

public class SkullCrusherAleAction extends AbstractGameAction {

    AbstractPlayer p;

    public SkullCrusherAleAction(){
        this.p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (!isDone){
            for (AbstractCard c : this.p.hand.group) {
                if (c.type == AbstractCard.CardType.ATTACK && c.cost > 0) {
                    c.costForTurn = c.cost - 1;
                    c.isCostModifiedForTurn = true;
                }
            }
            for (AbstractCard c : this.p.discardPile.group) {
                if (c.type == AbstractCard.CardType.ATTACK && c.cost > 0) {
                    c.costForTurn = c.cost - 1;
                    c.isCostModifiedForTurn = true;
                }
            }
            for (AbstractCard c : this.p.drawPile.group) {
                if (c.type == AbstractCard.CardType.ATTACK && c.cost > 0) {
                    c.costForTurn = c.cost - 1;
                    c.isCostModifiedForTurn = true;
                }
            }
            for (AbstractCard c : this.p.exhaustPile.group) {
                if (c.type == AbstractCard.CardType.ATTACK && c.cost > 0) {
                    c.costForTurn = c.cost - 1;
                    c.isCostModifiedForTurn = true;
                }
            }
            isDone = true;
        }
    }
}
