package drgmod.cards.uncommon.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.DeepPocketsPower;
import drgmod.util.CardStats;

public class DeepPockets extends BaseCard {
    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 3;

    public static final String ID = makeID(DeepPockets.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public DeepPockets() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DeepPocketsPower(p, magicNumber)));
    }
}
