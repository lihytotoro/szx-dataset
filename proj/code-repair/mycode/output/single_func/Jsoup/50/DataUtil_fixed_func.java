    static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
        String docData;
        Document doc = null;
        if (doc == null) {
            doc = parser.parseInput(docData, baseUri);
            doc.outputSettings().charset(charsetName);
        }
        return doc;
    }
