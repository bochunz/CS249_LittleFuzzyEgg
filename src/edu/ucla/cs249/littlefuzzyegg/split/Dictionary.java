package edu.ucla.cs249.littlefuzzyegg.split;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.ucla.cs249.littlefuzzyegg.data.Product;
import edu.ucla.cs249.littlefuzzyegg.tfidf.Tag;

public class Dictionary {
	private static int ELIMINATE_LENGTH = 2;
	private Set<String> words;
	private Set<String> importantWords;
	private static Dictionary instance = null;
	
	private Dictionary() {
		words = new HashSet<String>(Arrays.asList(
	            "asylum",
	            "arkham",
	            "bioshock",
	            "gears",
	            "warfare",
	            "warfare3",
	            "brotherhood",
	            "redemption",
	            "creed",
	            "company",
	            "cursed",
	            "marines",
	            "kinect",
	            "skylanders",
	            "black ops",
	            "guitar",
	            "guitar hero",
	            "legendary",
	            "spider",
	            "addition",
	            "insect",
	            "island",
	            "skyrim",
	            "forza4",
	            "tenkaichi",
	            "pirates",
	            "assassins",
	            "assassin",
	            "monopoly",
	            "rocksmith",
	            "fifa",
	            "battle",
	            "field",
	            "battlefield",
	            "battlefield3",
	            "batman",
	            "borderlands",
	            "borderlands2",
	            "bulletstorm",
	            "midnight club",
	            "katmai",
	            "rayman",
	            "assault",
	            "scrolls",
	            "personal",
	            "collectors",
	            "collector",
	            "editor",
	            "warrior",
	            "edition",
	            "central",
	            "advanced",
	            "transformers",
	            "wipeout",
	            "deadrising",
	            "deadrising2",
	            "motionsports",
	            "motorsport",
	            "motorsports",
	            "motorsports4",
	            "motorsport4",
	            "wolfenstein",
	            "turtle beaches",
	            "ultimate",
	            "unleashed",
	            "forza",
	            "gunstringer",
	            "destiny",
	            "warriors",
	            "hardened",
	            "infinite",
	            "modern warfare",
	            "modern",
	            "revelations",
	            "revelation",
	            "evolved",
	            "revelation",
	            "limited",
	            "star wars",
	            "cabelas",
	            "calibur",
	            "tekken",
	            "warhammer",
	            "hitman",
	            "jurassic",
	            "madden",
	            "just dance",
	            "collection",
	            "legendary",
	            "crysis",
	            "dragonball",
	            "dragonball z",
	            "midnight",
	            "minecraft",
	            "oblivion",
	            "rocksmith",
	            "homefront",
	            "forza motor",
	            "forza",
	            "noire",
	            "soldier",
	            "xbox 360",
	            "trainer",
	            "spiderman",
	            "sniper",
	            "raider",
	            "saboteur",
	            "katamari",
	            "anniversary",
	            "steering",
	            "zumba",
	            "resident",
	            "champion",
	            "san francisco",
	            "honor",
	            "kinetic",
	            "wheel",
	            "effect",
	            "cybertron",
	            "dynasty",
	            "allstars",
	            "yoostar",
	            "francisco",
	            "shift",
	            "shaddai",
	            "splatterhouse",
	            "deus",
	            "xmen"
	    ));
		importantWords = new HashSet<String> (Arrays.asList(
				"fifa",
				"ncis",
				"wolfenstein",
				"otomedius",
				"borderlands",
				"shaddai",
				"tropico",
				"lucha",
				"fable",
				"yoostar",
				"brink",
				"sixstring",
				"divinity",
				"zumba",
				"crysis",
				"libre",
				"sonic",
				"rapala",
				"uncaged",
				"kengo",
				"megamind",
				"kinectimals",
				"fallout",
				"singularity",
				"halo",
				"dreamworks",
				"jasf",
				"nfl",
				"nickelodeon",
				"hogan",
				"persia",
				"vanquish",
				"battlefield",
				"spongebob",
				"creed",
				"wwe",
				"bulletstorm",
				"xmen",
				"mysims",
				"nba",
				"rez",
				"homefront",
				"jurassic",
				"disney",
				"grease",
				"transformers",
				"udraw",
				"ufc",
				"guitar",
				"capcom",
				"gauntlet",
				"mma",
				"redemption",
				"ncaa",
				"rocksmith",
				"thor",
				"mindjack",
				"shred",
				"overstrike",
				"assassin",
				"katamari",
				"cabela",
				"pga",
				"nhl",
				"catherine",
				"goldeneye",
				"adrenalin",
				"madagascar",
				"lego",
				"facebreaker",
				"blackwater",
				"madden",
				"prototype",
				"darkstar",
				"batman",
				"forza",
				"nukem",
				"brunswick",
				"hasbro",
				"bodycount",
				"castlevania",
				"nascar",
				"mayhem",
				"armageddon",
				"ssx",
				"csi",
				"popcap",
				"gunstringer",
				"fracture",
				"deus",
				"tekken",
				"darksiders",
				"arcania",
				"agarest",
				"konami",
				"n3",
				"marvel"));
		
	}
	
	public static Dictionary getInstance() {
		if (instance == null)
			instance = new Dictionary();
		return instance;
	}
	
	public void importWords (List<String> wordList) {
		for (String word : wordList) {
			// we just add word that is longer than ELIMINATE_LENGTH into the dict
			if (word.length() > ELIMINATE_LENGTH && word.matches("[a-zA-Z]+"))
				words.add(word);
		}
	}
	
	public boolean isImportant(Tag tag) {		
		if (tag.getType() == Tag.Type.ACRONYM)
			return false;
		else if (tag.getType() == Tag.Type.ALSO) 
			return false;
		else if (tag.getType() == Tag.Type.DATE)
			return false;
		else 
			return importantWords.contains(tag.getValue()); 
	}
	
	public List<String> getList() {
		return new ArrayList<String> (words);
	}
	
	public int getSize() {
		return words.size();
	}
}

