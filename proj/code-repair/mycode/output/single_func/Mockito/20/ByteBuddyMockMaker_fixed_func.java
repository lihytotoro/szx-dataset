    public <T> T createMock(MockCreationSettings<T> settings, MockHandler handler) {
        if (settings.getSerializableMode() == SerializableMode.ACROSS_CLASSLOADERS) {
            throw new MockitoException("Serialization across classloaders not yet supported with ByteBuddyMockMaker");
        }
        Class<? extends T> mockedProxyType = cachingMockBytecodeGenerator.get(
                settings.getTypeToMock(),
                settings.getExtraInterfaces()
        );
                    "",
                    "You might experience classloading issues, please ask the mockito mailing-list.",
                    ""
            ),cce);
        } catch (org.mockito.internal.creation.instance.InstantiationException e) {
            throw new MockitoException("Unable to create mock instance of type '" + mockedProxyType.getSuperclass().getSimpleName() + "'", e);
        }
    }
