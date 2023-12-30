package drgmod.cards.common.skills;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.actions.ZiplineLauncherAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.util.CardStats;

public class ZiplineLauncher extends BaseCard {
    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;


    public static final String ID = makeID(ZiplineLauncher.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public ZiplineLauncher() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ZiplineLauncherAction(p));
        addToBot(new GainBlockAction(p, block));
    }
}

//    public void update() {
//        if (this.duration == this.startingDuration) {
//            for (AbstractPower p : AbstractDungeon.player.powers)
//                p.onScry();
//            if (AbstractDungeon.player.drawPile.isEmpty()) {
//                this.isDone = true;
//                return;
//            }
//            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
//            if (this.amount != -1) {
//                for (int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); i++)
//                    tmpGroup.addToTop(AbstractDungeon.player.drawPile.group
//                            .get(AbstractDungeon.player.drawPile.size() - i - 1));
//            } else {
//                for (AbstractCard c : AbstractDungeon.player.drawPile.group)
//                    tmpGroup.addToBottom(c);
//            }
//            AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, true, TEXT[0]);
//        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
//            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
//                AbstractDungeon.player.drawPile.moveToDiscardPile(c);
//            AbstractDungeon.gridSelectScreen.selectedCards.clear();
//        }
//        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
//            c.triggerOnScry();
//        tickDuration();
//    }
//}
