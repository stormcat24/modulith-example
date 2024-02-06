package dev.stormcat.tools.nlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;
import java.util.Properties;

public class WordAnalyzer {

    private StanfordCoreNLP pipeline;

    public WordAnalyzer() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
        pipeline = new StanfordCoreNLP(props);
    }

    CoreDocument createCoreDocument(String text) {
        String trimText = text.trim();
        if (trimText.isEmpty()) {
            throw new IllegalArgumentException("text is empty");
        }

        if (trimText.contains(" ")) {
            throw new IllegalArgumentException("text should not contain space");
        }

        return new CoreDocument(trimText);
    }

    public boolean isSingular(String text) {
        CoreDocument document = createCoreDocument(text);
        pipeline.annotate(document);

        List<CoreLabel> tokens = document.tokens();
        if (tokens.isEmpty()) {
            throw new IllegalStateException("tokens is empty");
        }

        CoreLabel token = tokens.getFirst();
        return "NN".equals(token.tag());
    }

    public String toLemma(String text) {
        CoreDocument document = createCoreDocument(text);
        pipeline.annotate(document);

        List<CoreLabel> tokens = document.tokens();
        if (tokens.isEmpty()) {
            throw new IllegalStateException("tokens is empty");
        }

        CoreLabel token = tokens.getFirst();
        return token.lemma();
    }

    public String toLemmaWithUnderscore(String text) {
        String[] words = text.split("_");
        String lastWord = words[words.length - 1];
        String lemmaLastWord = toLemma(lastWord);
        words[words.length - 1] = lemmaLastWord;
        return String.join("_", words);
    }
}
