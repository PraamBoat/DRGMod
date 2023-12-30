package drgmod.cards.uncommon.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.IntoxicationPower;
import drgmod.powers.MineralPower;
import drgmod.util.CardStats;
import drgmod.util.CustomTags;

public class DizzySwing extends BaseCard {

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;


    public static final String ID = makeID(DizzySwing.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public DizzySwing() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(CustomTags.PICKAXE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(IntoxicationPower.POWER_ID)){
            for (int i = 0; i < p.getPower(IntoxicationPower.POWER_ID).amount; i++){
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }
}
