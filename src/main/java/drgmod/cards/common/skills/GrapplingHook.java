package drgmod.cards.common.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Flex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.*;
import drgmod.util.CardStats;
import drgmod.util.CustomTags;

public class GrapplingHook extends BaseCard {
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 13;
    private static final boolean INNATE = true;
    private static final boolean EXHAUST = true;
    private static final int DRAWAMOUNT = 1;

    public static final String ID = makeID(GrapplingHook.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public GrapplingHook() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        setInnate(INNATE);
        setExhaust(EXHAUST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster k : AbstractDungeon.getMonsters().monsters){
            addToBot(new DrawCardAction(p, DRAWAMOUNT));
        }
        if (this.upgraded){
            addToBot(new GainBlockAction(p, p, block));
        }
    }
}
