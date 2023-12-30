package drgmod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import drgmod.powers.BurnPower;
import drgmod.powers.MineralPower;

public class BiozoneDuplicationAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DualWieldAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private int damage;
    private boolean open;
    private AbstractCreature target;

    public BiozoneDuplicationAction(AbstractPlayer p, int amount){
        this.p = p;
        this.amount = amount;
        this.open = false;
    }

    @Override
    public void update() {
        while (!isDone){
            if (!open){
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount,true, true);
                open = true;
                return;
            }
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
                addToBot(new DamageAction(this.target, new DamageInfo(this.p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                for (AbstractCard c: AbstractDungeon.handCardSelectScreen.selectedCards.group){
                    this.p.hand.moveToHand(c);
                    AbstractCard k = c.makeCopy();
                    if (c.upgraded){
                        k.upgrade();
                    }
                    k.isEthereal = true;
                    k.exhaust = true;
                    k.isGlowing = true;
                    k.glowColor = Color.LIME;
                    this.p.hand.moveToHand(k);
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                isDone = true;
            }
        }
    }
}
