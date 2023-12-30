package drgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.cards.common.attacks.ThunderheadHeavyAutocannon;

public class ThunderheadAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private int damage;
    private DamageInfo info;
    private boolean open;
    private boolean upgraded;
    public ThunderheadAction(AbstractPlayer p, int damage, boolean upgraded){
        this.p = p;
        this.damage = damage;
        this.open = false;
        if (upgraded){
            this.amount = 3;
        }
        else{
            this.amount = 2;
        }
        this.upgraded = upgraded;
    }

    public ThunderheadAction(AbstractPlayer p, DamageInfo info){
        this.p = p;
        this.info = info;
    }

    @Override
    public void update() {
        while (!isDone){
            if (this.p.hand.size() == 0){
                this.isDone = true;
                return;
            }
            if (!open){
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount,true, true);
                open = true;
                return;
            }
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
                for (AbstractCard c: AbstractDungeon.handCardSelectScreen.selectedCards.group){
                    this.p.hand.moveToDiscardPile(c);
                    GameActionManager.incrementDiscard(false);
//                    addToBot(new DamageAllEnemiesAction(this.p, this.damage, DamageInfo.DamageType.NORMAL, AttackEffect.FIRE));
                    addToBot(new DamageAllEnemiesAction(this.p, ThunderheadHeavyAutocannon.dam, DamageInfo.DamageType.NORMAL, AttackEffect.FIRE));

//                    for (AbstractMonster m: AbstractDungeon.getMonsters().monsters){
//                        addToBot(new DamageAction(m, this.info, AttackEffect.FIRE));
//                    }
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                isDone = true;
            }
        }
    }
}
