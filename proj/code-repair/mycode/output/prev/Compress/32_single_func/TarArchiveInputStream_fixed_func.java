    private void applyPaxHeadersToCurrentEntry(Map<String, String> headers) {
        for (Entry<String, String> ent : headers.entrySet()) {
            String key = ent.getKey();
            String val = ent.getValue();
            if ("path".equals(key)) {
                currEntry.setName(val);
            } else if ("linkpath".equals(key)) {
                currEntry.setLinkName(val);
            } else if ("gid".equals(key)) {
                currEntry.setGroupId(Long.parseLong(val, 8));
            } else if ("gname".equals(key)) {
                currEntry.setGroupName(val);
            } else if ("uid".equals(key)) {
                currEntry.setUserId(Long.parseLong(val, 8));
            } else if ("uname".equals(key)) {
                currEntry.setUserName(val);
            } else if ("size".equals(key)) {
                currEntry.setSize(Long.parseLong(val));
            } else if ("mtime".equals(key)) {
                currEntry.setModTime((long) (Double.parseDouble(val) * 1000));
            } else if ("SCHILY.devminor".equals(key)) {
                currEntry.setDevMinor(Integer.parseInt(val));
            } else if ("SCHILY.devmajor".equals(key)) {
                currEntry.setDevMajor(Integer.parseInt(val));
            }
        }
    }
